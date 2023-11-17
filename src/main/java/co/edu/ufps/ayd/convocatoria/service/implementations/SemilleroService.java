package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.ufps.ayd.convocatoria.exception.SemilleroException;
import co.edu.ufps.ayd.convocatoria.model.entity.SemilleroEntity;
import co.edu.ufps.ayd.convocatoria.repository.SemilleroRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.SemilleroInterface;

@Service
public class SemilleroService implements SemilleroInterface{

    @Autowired
    SemilleroRepository semilleroRepository;

    @Override
    public List<SemilleroEntity> listarSemilleros() {
        List<SemilleroEntity> semilleroEntity =  semilleroRepository.findAll();
        if(semilleroEntity.isEmpty()){
            throw new SemilleroException("No hay semilleros registrados");
        }
        return semilleroEntity;
    }

    @Override
    public List<SemilleroEntity> listarSemillerosActivos() {
        List<SemilleroEntity> semilleroEntity =  semilleroRepository.findAllByEstado(true);
        if(semilleroEntity.isEmpty()){
            throw new SemilleroException("No hay semilleros activos");
        }
        return semilleroEntity;
    }

    @Override
    public SemilleroEntity buscarSemillero(Integer id) {
        Optional<SemilleroEntity> semilleroEntity =  semilleroRepository.findById(id);
        if(!semilleroEntity.isPresent()){
            throw new SemilleroException("No hay un semillero registrado  con el id" + id);
        }
        return semilleroEntity.get();
    }

    @Override
    public void agregarSemillero(SemilleroEntity semilleroEntity) {
        Optional<SemilleroEntity> semilleroOptional = semilleroRepository.findByCodigo(semilleroEntity.getCodigo());
        if(semilleroOptional.isPresent()){
            throw new SemilleroException("Ya hay un semillero registrado con el codigo: " + semilleroEntity.getCodigo() );
        }
        semilleroEntity.setEstado(true);
        semilleroRepository.save(semilleroEntity);
    }

    @Override
    public void modificarSemillero(Integer id, SemilleroEntity semilleroEntity) {
        Optional<SemilleroEntity> semilleroOptional = semilleroRepository.findById(id);
        if(!semilleroOptional.isPresent()){
            throw new SemilleroException("No hay un semillero registrado con el codigo: " + semilleroEntity.getCodigo() );
        }
        SemilleroEntity semilleroActualizado = semilleroOptional.get();
        semilleroActualizado.setCodigo(semilleroEntity.getCodigo());
        semilleroActualizado.setNombre(semilleroEntity.getNombre());
        semilleroRepository.save(semilleroActualizado);
    }

    @Override
    public void inhabilitarSemillero(Integer id) {
        Optional<SemilleroEntity> semilleroOptional = semilleroRepository.findById(id);
        if(!semilleroOptional.isPresent()){
            throw new SemilleroException("No hay un semillero registrado con el id" + id);
        }
        SemilleroEntity semilleroEntity = semilleroOptional.get();
        semilleroEntity.setEstado(!semilleroEntity.getEstado());
        semilleroRepository.save(semilleroEntity);
    }
    
}
