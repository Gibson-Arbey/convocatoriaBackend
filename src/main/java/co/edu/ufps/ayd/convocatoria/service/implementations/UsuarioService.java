package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.ufps.ayd.convocatoria.exception.CodigoExistsException;
import co.edu.ufps.ayd.convocatoria.exception.CodigoInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.EmailExistsException;
import co.edu.ufps.ayd.convocatoria.exception.EmailInvalidoException;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.repository.RolRepository;
import co.edu.ufps.ayd.convocatoria.repository.UsuarioRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.UsuarioInterface;

@Service
public class UsuarioService implements UsuarioInterface{
    
    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private  RolRepository rolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity userEntity = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (userEntity.getRol() != null) {
            authorities.add(new SimpleGrantedAuthority(userEntity.getRol().getNombre()));
        } else {
            throw new UsernameNotFoundException(
                    "Error en el Login: usuario '" + username + "' no tiene roles asignados!");
        }
        return new User(userEntity.getEmail(), userEntity.getContrasenia(), authorities);
    }

    @Override
    public void guardarUsuario(UsuarioEntity usuarioEntity, String rolNombre) {
        if (usuarioRepository.findByEmail(usuarioEntity.getEmail()).isPresent()) {
            throw new EmailExistsException("El correo electronico ya existe");
        }

        if (usuarioRepository.findBycodigo(usuarioEntity.getCodigo()).isPresent()) {
            throw new CodigoExistsException("El codigo ya esta registrado");
        }

        if(usuarioEntity.getCodigo().length() != 5 && usuarioEntity.getCodigo().length() != 7){
            throw new CodigoInvalidoException("El codigo no es valido");
        }

        if (!usuarioEntity.getEmail().toLowerCase().endsWith("@ufps.edu.co")) {
            throw new EmailInvalidoException("El correo electrónico debe terminar en @ufps.edu.co");
        }
        
        try {

            usuarioEntity.setEstado(true);
            usuarioEntity.setRol(rolRepository.findByNombre(rolNombre));
            usuarioRepository.save(usuarioEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar al usuario:" + e.getMessage(), e);
        }
    }
}
