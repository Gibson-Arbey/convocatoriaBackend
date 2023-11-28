package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.ufps.ayd.convocatoria.exception.PropuestaException;
import co.edu.ufps.ayd.convocatoria.model.dto.PropuestaDTO;
import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponentePropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.repository.ConvocatoriaRepository;
import co.edu.ufps.ayd.convocatoria.repository.ProponentePropuestaRepository;
import co.edu.ufps.ayd.convocatoria.repository.ProponenteRepository;
import co.edu.ufps.ayd.convocatoria.repository.PropuestaRepository;
import co.edu.ufps.ayd.convocatoria.repository.UsuarioRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.PropuestaInterface;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PropuestaService implements PropuestaInterface {

    @Autowired
    ProponenteRepository proponenteRepository;

    @Autowired
    PropuestaRepository propuestaRepository;

    @Autowired
    ProponentePropuestaRepository proponentePropuestaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    EmailService emailService;

    @Override
    @Transactional
    public void registrarPropuesta(PropuestaEntity propuestaEntity, List<ProponenteEntity> proponentes) {
        if (("S".equals(propuestaEntity.getTipo()) && propuestaEntity.getMateria() != null) ||
                ("M".equals(propuestaEntity.getTipo()) && propuestaEntity.getSemillero() != null)) {
            throw new PropuestaException("No se puede combinar el tipo de propuesta");
        }
        PropuestaEntity nuevaPropuesta = propuestaRepository.save(propuestaEntity);
        for (ProponenteEntity proponente : proponentes) {
            ProponenteEntity proponenteEntity = proponenteRepository.save(proponente);
            proponentePropuestaRepository.save(new ProponentePropuestaEntity(nuevaPropuesta, proponenteEntity));
        }
    }

    @Override
    public List<PropuestaEntity> listarPropuestasRegistradasDelUsuario(String email) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(email);
        if (!usuarioEntity.isPresent()) {
            throw new PropuestaException("El usuario no ha registrado alguna propuesta");
        }
        return propuestaRepository.findByUsuario(usuarioEntity.get());
    }

    @Override
    public void asignarEvaluador(Integer idPropuesta, Integer idEvaluador) {
        Optional<PropuestaEntity> propuestaOptional = propuestaRepository.findById(idPropuesta);
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(idEvaluador);
        if (!propuestaOptional.isPresent()) {
            throw new PropuestaException("No hay una propuesta registrada con el id" + idPropuesta);
        }
        if (!usuarioOptional.isPresent()) {
            throw new PropuestaException("No hay un evaluador registrado con el id" + idEvaluador);
        }
        if (usuarioOptional.get().getEstado() == false) {
            throw new PropuestaException("No se puede asignar un evaluador inhabilitado");
        }
        PropuestaEntity propuestaEntity = propuestaOptional.get();
        propuestaEntity.setEvaluador(usuarioOptional.get());
        propuestaRepository.save(propuestaEntity);
        emailService.notificarEvaluador(usuarioOptional.get().getEmail(), propuestaEntity.getNombre());
    }

    @Override
    public void calificarPropuestaAsignada(Integer idPropuesta, Integer puntaje) {
        Optional<PropuestaEntity> propuestaOptional = propuestaRepository.findById(idPropuesta);
        if (!propuestaOptional.isPresent()) {
            throw new PropuestaException("No hay una propuesta registrada con el id" + idPropuesta);
        }

        if (puntaje < 0 || puntaje > 100) {
            throw new PropuestaException("Puntaje invalido, debe ser de 0 a 100");
        }
        PropuestaEntity propuestaEntity = propuestaOptional.get();
        if (propuestaEntity.getPuntaje() == null) {

            propuestaEntity.setPuntaje(puntaje);
            propuestaRepository.save(propuestaEntity);
        } else {
            throw new PropuestaException("La propuesta ya tiene un puntaje registrado.");
        }
    }

    @Override
    public void calificarPropuestaAdmin(Integer idPropuesta, Integer puntaje) {
        Optional<PropuestaEntity> propuestaOptional = propuestaRepository.findById(idPropuesta);
        if (!propuestaOptional.isPresent()) {
            throw new PropuestaException("No hay una propuesta registrada con el id" + idPropuesta);
        }

        if (puntaje < 0 || puntaje > 100) {
            throw new PropuestaException("Puntaje invalido, debe ser de 0 a 100");
        }
        PropuestaEntity propuestaEntity = propuestaOptional.get();
        propuestaEntity.setPuntaje(puntaje);
        propuestaRepository.save(propuestaEntity);
    }

    @Override
    public List<PropuestaDTO> listarPropuestasAgrupadasPorTipo() {
        Optional<ConvocatoriaEntity> convocatorOptional = convocatoriaRepository.findByEstado(true);
        List<PropuestaEntity> propuestas = propuestaRepository.findByConvocatoria(convocatorOptional.get());

        Map<String, List<PropuestaEntity>> propuestasAgrupadasPorTipo = propuestas.stream()
                .collect(Collectors.groupingBy(PropuestaEntity::getTipo));

        return propuestasAgrupadasPorTipo.entrySet().stream()
                .flatMap(entry -> {
                    String tipo = entry.getKey();
                    List<PropuestaEntity> propuestasPorTipo = entry.getValue();

                    return propuestasPorTipo.stream()
                            .map(propuesta -> {
                                List<ProponentePropuestaEntity> proponentePropuestaEntities = proponentePropuestaRepository
                                        .findByPropuesta(propuesta);

                                List<ProponenteEntity> proponentes = proponentePropuestaEntities.stream()
                                        .map(ProponentePropuestaEntity::getProponente)
                                        .collect(Collectors.toList());

                                PropuestaDTO propuestaDTO = new PropuestaDTO();
                                propuestaDTO.setId(propuesta.getId());
                                propuestaDTO.setNombre(propuesta.getNombre());
                                propuestaDTO.setConvocatoria(propuesta.getConvocatoria());
                                propuestaDTO.setProponentes(proponentes);
                                propuestaDTO.setPuntaje(propuesta.getPuntaje());
                                propuestaDTO.setTipo(tipo);
                                propuestaDTO.setMateria(propuesta.getMateria());
                                propuestaDTO.setSemillero(propuesta.getSemillero());
                                propuestaDTO.setProfesor(propuesta.getProfesor());
                                propuestaDTO.setArchivo(propuesta.getArchivo());

                                return propuestaDTO;
                            });
                })
                .collect(Collectors.toList());
    }

    @Override
    public PropuestaDTO obtenerPropuestaPorId(Integer id) {
        PropuestaEntity propuestaEntity = propuestaRepository.findById(id)
                .orElseThrow(() -> new PropuestaException("Propuesta no encontrada con ID: " + id));

        return convertirAPropuestaDTO(propuestaEntity);
    }

    @Override
    @Transactional
    public void modificarPropuesta(Integer id, PropuestaDTO propuestaDTO) {
        PropuestaEntity propuestaExistente = propuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propuesta no encontrada con ID: " + id));

        propuestaExistente.setNombre(propuestaDTO.getNombre());
        propuestaExistente.setProfesor(propuestaDTO.getProfesor());
        propuestaExistente.setTipo(propuestaDTO.getTipo());
        propuestaExistente.setMateria(propuestaDTO.getMateria());
        propuestaExistente.setSemillero(propuestaDTO.getSemillero());

        if (("S".equals(propuestaExistente.getTipo()) && propuestaExistente.getMateria() != null) ||
                ("M".equals(propuestaExistente.getTipo()) && propuestaExistente.getSemillero() != null)) {
            throw new PropuestaException("No se puede combinar el tipo de propuesta");
        }
        List<ProponenteEntity> proponentesDTO = propuestaDTO.getProponentes();

        if (proponentesDTO != null) {
            for (ProponenteEntity proponenteDTO : proponentesDTO) {
                ProponenteEntity proponenteExistente = proponenteRepository.findById(proponenteDTO.getId())
                        .orElseThrow(() -> new PropuestaException(
                                "Proponente no encontrado con ID: " + proponenteDTO.getId()));

                proponenteExistente.setCodigo(proponenteDTO.getCodigo());
                proponenteExistente.setNombre(proponenteDTO.getNombre());
                proponenteExistente.setCedula(proponenteDTO.getCedula());
                proponenteExistente.setSemestre(proponenteDTO.getSemestre());

                proponenteRepository.save(proponenteExistente);

            }
        }

        propuestaRepository.save(propuestaExistente);
    }

    @Override
    public List<PropuestaDTO> listarAsignadas(UsuarioEntity usuarioEntity) {
        List<PropuestaEntity> propuestasAsignadas = propuestaRepository.findByEvaluador(usuarioEntity);

        if (propuestasAsignadas.isEmpty()) {
            throw new PropuestaException("No hay propuestas asignadas al evaluador.");
        }

        return propuestasAsignadas.stream()
                .map(propuesta -> {
                    List<ProponentePropuestaEntity> proponentePropuestaEntities = proponentePropuestaRepository
                            .findByPropuesta(propuesta);

                    List<ProponenteEntity> proponentes = proponentePropuestaEntities.stream()
                            .map(ProponentePropuestaEntity::getProponente)
                            .collect(Collectors.toList());

                    PropuestaDTO propuestaDTO = new PropuestaDTO();
                    propuestaDTO.setId(propuesta.getId());
                    propuestaDTO.setNombre(propuesta.getNombre());
                    propuestaDTO.setConvocatoria(propuesta.getConvocatoria());
                    propuestaDTO.setProponentes(proponentes);
                    propuestaDTO.setPuntaje(propuesta.getPuntaje());
                    propuestaDTO.setTipo(propuesta.getTipo());
                    propuestaDTO.setMateria(propuesta.getMateria());
                    propuestaDTO.setSemillero(propuesta.getSemillero());
                    propuestaDTO.setProfesor(propuesta.getProfesor());
                    propuestaDTO.setArchivo(propuesta.getArchivo());

                    return propuestaDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PropuestaDTO> listarPropuestasPuntajeDescendente() {
        Optional<ConvocatoriaEntity> convocatoriaOptional = convocatoriaRepository.findByEstado(true);

        if (convocatoriaOptional.isPresent()) {
            List<PropuestaEntity> propuestasOrdenadas = propuestaRepository.findByConvocatoriaOrderByPuntajeDesc(convocatoriaOptional.get());
            return propuestasOrdenadas.stream()
                    .map(propuesta -> {
                        List<ProponentePropuestaEntity> proponentePropuestaEntities = proponentePropuestaRepository
                                .findByPropuesta(propuesta);

                        List<ProponenteEntity> proponentes = proponentePropuestaEntities.stream()
                                .map(ProponentePropuestaEntity::getProponente)
                                .collect(Collectors.toList());

                        PropuestaDTO propuestaDTO = new PropuestaDTO();
                        propuestaDTO.setId(propuesta.getId());
                        propuestaDTO.setNombre(propuesta.getNombre());
                        propuestaDTO.setConvocatoria(propuesta.getConvocatoria());
                        propuestaDTO.setProponentes(proponentes);
                        propuestaDTO.setPuntaje(propuesta.getPuntaje());
                        propuestaDTO.setTipo(propuesta.getTipo());
                        propuestaDTO.setMateria(propuesta.getMateria());
                        propuestaDTO.setSemillero(propuesta.getSemillero());
                        propuestaDTO.setProfesor(propuesta.getProfesor());
                        propuestaDTO.setArchivo(propuesta.getArchivo());

                        return propuestaDTO;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new PropuestaException("No hay una convocatoria habilitada.");
        }

    }

    public PropuestaDTO convertirAPropuestaDTO(PropuestaEntity propuestaEntity) {
        PropuestaDTO propuestaDTO = new PropuestaDTO();
        BeanUtils.copyProperties(propuestaEntity, propuestaDTO);
        propuestaDTO.setProponentes(propuestaEntity.getProponentes().stream()
                .map(ProponentePropuestaEntity::getProponente)
                .collect(Collectors.toList()));
        return propuestaDTO;
    }

    @Override
    public List<ConvocatoriaEntity> listarConvocatoriasPasadas() {
        List<ConvocatoriaEntity> convocatoriasPasadas = convocatoriaRepository.findAllByEstado(false);
        if(convocatoriasPasadas.isEmpty()){
             throw new PropuestaException("No hay convocatorias pasadas.");
        }
        return convocatoriasPasadas;
    }

    @Override
    public List<PropuestaDTO> listarInfoConvocatoriaPasada(Integer id) {
        Optional<ConvocatoriaEntity> convocatoriaOptional = convocatoriaRepository.findById(id);

        if (convocatoriaOptional.isPresent()) {
            List<PropuestaEntity> propuestasOrdenadas = propuestaRepository.findByConvocatoriaOrderByPuntajeDesc(convocatoriaOptional.get());
            return propuestasOrdenadas.stream()
                    .map(propuesta -> {
                        List<ProponentePropuestaEntity> proponentePropuestaEntities = proponentePropuestaRepository
                                .findByPropuesta(propuesta);

                        List<ProponenteEntity> proponentes = proponentePropuestaEntities.stream()
                                .map(ProponentePropuestaEntity::getProponente)
                                .collect(Collectors.toList());

                        PropuestaDTO propuestaDTO = new PropuestaDTO();
                        propuestaDTO.setId(propuesta.getId());
                        propuestaDTO.setNombre(propuesta.getNombre());
                        propuestaDTO.setConvocatoria(propuesta.getConvocatoria());
                        propuestaDTO.setProponentes(proponentes);
                        propuestaDTO.setPuntaje(propuesta.getPuntaje());
                        propuestaDTO.setTipo(propuesta.getTipo());
                        propuestaDTO.setMateria(propuesta.getMateria());
                        propuestaDTO.setSemillero(propuesta.getSemillero());
                        propuestaDTO.setProfesor(propuesta.getProfesor());
                        propuestaDTO.setArchivo(propuesta.getArchivo());

                        return propuestaDTO;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new PropuestaException("No hay una convocatoria con el id:" + id);
        }
    }

}
