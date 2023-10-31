package co.edu.ufps.ayd.convocatoria.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

public interface UsuarioInterface extends UserDetailsService{

    public void guardarUsuario(UsuarioEntity usuarioEntity, String rolNombre);

}
