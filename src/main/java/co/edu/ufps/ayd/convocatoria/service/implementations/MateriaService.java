package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.ayd.convocatoria.exception.MateriaException;
import co.edu.ufps.ayd.convocatoria.model.entity.MateriaEntity;
import co.edu.ufps.ayd.convocatoria.repository.MateriaRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.MateriaInterface;

@Service
public class MateriaService implements MateriaInterface{

    @Autowired
    private MateriaRepository materiaRepository;
    
    @Override
    public List<MateriaEntity> listarMaterias() {
        List<MateriaEntity> materiaEntity =  materiaRepository.findAll();
        if(materiaEntity.isEmpty()){
            throw new MateriaException("No hay materias registradas");
        }
        return materiaEntity;
    }

    @Override
    public List<MateriaEntity> listarMateriasActivas() {
        List<MateriaEntity> materiaEntity =  materiaRepository.findAllByEstado(true);
        if(materiaEntity.isEmpty()){
            throw new MateriaException("No hay materias activas");
        }
        return materiaEntity;
    }

    @Override
    public MateriaEntity buscarMateria(Integer id) {
        Optional<MateriaEntity> materiaEntity =  materiaRepository.findById(id);
        if(!materiaEntity.isPresent()){
            throw new MateriaException("No hay una materia registrada  con el id" + id);
        }
        return materiaEntity.get();
    }

    @Override
    public void agregarMateria(MateriaEntity materiaEntity) {
        Optional<MateriaEntity> materiaOptional = materiaRepository.findByCodigoAndGrupo(materiaEntity.getCodigo(), materiaEntity.getGrupo());
        if(materiaOptional.isPresent()){
            throw new MateriaException("Ya hay una materia registrada con el codigo: " + materiaEntity.getCodigo() + "y grupo: " + materiaEntity.getGrupo());
        }
        materiaEntity.setEstado(true);
        materiaRepository.save(materiaEntity);
    }

    @Override
    public void modificarMateria(Integer id, MateriaEntity materiaEntity) {
        Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
        if(!materiaOptional.isPresent()){
            throw new MateriaException("No hay una materia registrada con el id" + id);
        }
        MateriaEntity materiaActualizada = materiaOptional.get();
        materiaActualizada.setCodigo(materiaEntity.getCodigo());
        materiaActualizada.setGrupo(materiaEntity.getGrupo());
        materiaActualizada.setNombre(materiaEntity.getNombre());
        materiaRepository.save(materiaActualizada);
    }

    @Override
    public void inhabilitarMateria(Integer id) {
        Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
        if(!materiaOptional.isPresent()){
            throw new MateriaException("No hay una materia registrada con el id" + id);
        }
        MateriaEntity materiaEntity = materiaOptional.get();
        materiaEntity.setEstado(!materiaEntity.getEstado());
        materiaRepository.save(materiaEntity);
    }
    
}
