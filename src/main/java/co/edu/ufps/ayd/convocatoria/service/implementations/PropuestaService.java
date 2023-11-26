package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.ufps.ayd.convocatoria.exception.PropuestaException;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponentePropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.repository.ProponentePropuestaRepository;
import co.edu.ufps.ayd.convocatoria.repository.ProponenteRepository;
import co.edu.ufps.ayd.convocatoria.repository.PropuestaRepository;
import co.edu.ufps.ayd.convocatoria.repository.UsuarioRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.PropuestaInterface;

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
        if(!propuestaOptional.isPresent()){
            throw new PropuestaException("No hay una propuesta registrada con el id" + idPropuesta); 
        }
        if(!usuarioOptional.isPresent()){
            throw new PropuestaException("No hay un evaluador registrado con el id" + idEvaluador); 
        }
        if(usuarioOptional.get().getEstado() == false) {
             throw new PropuestaException("No se puede asignar un evaluador inhabilitado"); 
        }
        PropuestaEntity propuestaEntity = propuestaOptional.get();
        propuestaEntity.setEvaluador(usuarioOptional.get());
        propuestaRepository.save(propuestaEntity);
        emailService.notificarEvaluador(usuarioOptional.get().getEmail(), propuestaEntity.getNombre()); 
    }


}
