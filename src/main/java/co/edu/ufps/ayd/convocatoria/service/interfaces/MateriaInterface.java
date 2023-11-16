package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.MateriaEntity;

public interface MateriaInterface {
    
    public List<MateriaEntity> listarMaterias();

    public List<MateriaEntity> listarMateriasActivas();

    public MateriaEntity buscarMateria(Integer id);

    public void agregarMateria(MateriaEntity materiaEntity);

    public void modificarMateria(Integer id, MateriaEntity materiaEntity);

    public void inhabilitarMateria(Integer id);
    
}
