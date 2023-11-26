package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;

public interface PropuestaInterface {

    public void registrarPropuesta(PropuestaEntity propuestaEntity, List<ProponenteEntity> proponentes);

    public List<PropuestaEntity> listarPropuestasRegistradasDelUsuario(String email);

    public void asignarEvaluador(Integer idPropuesta, Integer idEvaluador);
}
