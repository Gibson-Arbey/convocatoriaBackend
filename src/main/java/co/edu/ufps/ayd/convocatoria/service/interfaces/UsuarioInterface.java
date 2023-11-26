package co.edu.ufps.ayd.convocatoria.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

public interface UsuarioInterface extends UserDetailsService{

    public void guardarUsuario(UsuarioEntity usuarioEntity, String rolNombre);

    public void reestablecerContrasenia(String email, String contrasenia, String contraseniaEncriptada);

    public UsuarioEntity buscarUsuarioEmail(String email);

    public Optional<List<UsuarioEntity>>  listarEvaluadores();

    public void inhabilitarEvaluador(Integer id);

}
