package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.dto.PropuestaDTO;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;

public interface PropuestaInterface {

    public void registrarPropuesta(PropuestaEntity propuestaEntity, List<ProponenteEntity> proponentes);

    public List<PropuestaEntity> listarPropuestasRegistradasDelUsuario(String email);

    public void asignarEvaluador(Integer idPropuesta, Integer idEvaluador);

    public void calificarPropuestaAsignada(Integer idPropuesta, Integer puntaje);

    public void calificarPropuestaAdmin(Integer idPropuesta, Integer puntaje);

    public List<PropuestaDTO> listarPropuestasAgrupadasPorTipo();

    public PropuestaDTO obtenerPropuestaPorId(Integer id);

    public void modificarPropuesta(Integer id, PropuestaDTO propuestaDTO);
}
