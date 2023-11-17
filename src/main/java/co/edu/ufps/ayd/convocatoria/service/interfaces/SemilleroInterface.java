package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.SemilleroEntity;

public interface SemilleroInterface {
    
    public List<SemilleroEntity> listarSemilleros();

    public List<SemilleroEntity> listarSemillerosActivos();

    public SemilleroEntity buscarSemillero(Integer id);

    public void agregarSemillero(SemilleroEntity semilleroEntity);

    public void modificarSemillero(Integer id, SemilleroEntity semilleroEntity);

    public void inhabilitarSemillero(Integer id);
}
