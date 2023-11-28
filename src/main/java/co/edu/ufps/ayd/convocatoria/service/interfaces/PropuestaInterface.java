package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.dto.PropuestaDTO;
import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

public interface PropuestaInterface {

    public void registrarPropuesta(PropuestaEntity propuestaEntity, List<ProponenteEntity> proponentes);

    public List<PropuestaEntity> listarPropuestasRegistradasDelUsuario(String email);

    public void asignarEvaluador(Integer idPropuesta, Integer idEvaluador);

    public void calificarPropuestaAsignada(Integer idPropuesta, Integer puntaje);

    public void calificarPropuestaAdmin(Integer idPropuesta, Integer puntaje);

    public List<PropuestaDTO> listarPropuestasAgrupadasPorTipo();

    public PropuestaDTO obtenerPropuestaPorId(Integer id);

    public void modificarPropuesta(Integer id, PropuestaDTO propuestaDTO);

    public List<PropuestaDTO> listarAsignadas(UsuarioEntity usuarioEntity);

    public List<PropuestaDTO> listarPropuestasPuntajeDescendente();

    public List<ConvocatoriaEntity> listarConvocatoriasPasadas();

    public List<PropuestaDTO> listarInfoConvocatoriaPasada(Integer id);
}
