package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.ProfesorEntity;

public interface ProfesorInterface {
    
    public List<ProfesorEntity> listarProfesores();

    public List<ProfesorEntity> listarProfesoresActivos();

    public ProfesorEntity buscarProfesor(Integer id);

    public void agregarProfesor(ProfesorEntity profesorEntity);

    public void modificarProfesor(Integer id, ProfesorEntity profesorEntity);

    public void inhabilitarProfesor(Integer id);
}
