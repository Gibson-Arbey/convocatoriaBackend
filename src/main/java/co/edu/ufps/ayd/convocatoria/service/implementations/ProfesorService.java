package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.ufps.ayd.convocatoria.exception.ProfesorException;
import co.edu.ufps.ayd.convocatoria.model.entity.ProfesorEntity;
import co.edu.ufps.ayd.convocatoria.repository.ProfesorRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.ProfesorInterface;

@Service
public class ProfesorService  implements ProfesorInterface{

    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    public List<ProfesorEntity> listarProfesores() {
        List<ProfesorEntity> profesorEntity =  profesorRepository.findAll();
        if(profesorEntity.isEmpty()){
            throw new ProfesorException("No hay profesores registrados");
        }
        return profesorEntity;
    }

    @Override
    public List<ProfesorEntity> listarProfesoresActivos() {
        List<ProfesorEntity> profesorEntity =  profesorRepository.findAllByEstado(true);
        if(profesorEntity.isEmpty()){
            throw new ProfesorException("No hay profesores activos");
        }
        return profesorEntity;
    }

    @Override
    public ProfesorEntity buscarProfesor(Integer id) {
        Optional<ProfesorEntity> profesorEntity =  profesorRepository.findById(id);
        if(!profesorEntity.isPresent()){
            throw new ProfesorException("No hay un profesor registrado  con el id" + id);
        }
        return profesorEntity.get();
    }

    @Override
    public void agregarProfesor(ProfesorEntity profesorEntity) {
        Optional<ProfesorEntity> profesorOptional = profesorRepository.findByCodigo(profesorEntity.getCodigo());
        if(profesorOptional.isPresent()){
            throw new ProfesorException("Ya hay un profesor registrado con el codigo: " + profesorEntity.getCodigo() );
        }
        profesorEntity.setEstado(true);
        profesorRepository.save(profesorEntity);
    }

    @Override
    public void modificarProfesor(Integer id, ProfesorEntity profesorEntity) {
        Optional<ProfesorEntity> profesorOptional = profesorRepository.findById(id);
        if(!profesorOptional.isPresent()){
            throw new ProfesorException("Ya hay un profesor registrado con el codigo: " + profesorEntity.getCodigo() );
        }
        ProfesorEntity profeosrActualizado = profesorOptional.get();
        profeosrActualizado.setCodigo(profesorEntity.getCodigo());
        profeosrActualizado.setNombre(profesorEntity.getNombre());
        profesorRepository.save(profeosrActualizado);
    }

    @Override
    public void inhabilitarProfesor(Integer id) {
        Optional<ProfesorEntity> profesorOptional = profesorRepository.findById(id);
        if(!profesorOptional.isPresent()){
            throw new ProfesorException("No hay un profesor registrado con el id" + id);
        }
        ProfesorEntity profesorEntity = profesorOptional.get();
        profesorEntity.setEstado(!profesorEntity.getEstado());
        profesorRepository.save(profesorEntity);
    }
    
}
