package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.ufps.ayd.convocatoria.exception.CodigoExistsException;
import co.edu.ufps.ayd.convocatoria.exception.CodigoInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.ContraseniaInvalidException;
import co.edu.ufps.ayd.convocatoria.exception.EmailExistsException;
import co.edu.ufps.ayd.convocatoria.exception.EmailInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.UsuarioException;
import co.edu.ufps.ayd.convocatoria.model.entity.RolEntity;
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

    @Autowired
    private EmailService emailService;

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

        if(usuarioEntity.getContrasenia().length() < 8){
            throw new ContraseniaInvalidException("La contraseña debe tener minimo 8 caracteres");
        }
        
        try {

            usuarioEntity.setEstado(true);
            usuarioEntity.setRol(rolRepository.findByNombre(rolNombre));
            usuarioRepository.save(usuarioEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar al usuario:" + e.getMessage(), e);
        }
    }

    @Override
    public void reestablecerContrasenia(String email, String contrasenia, String contraseniaEncriptada) {
            UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).get();
            usuarioEntity.setContrasenia(contraseniaEncriptada);
            usuarioRepository.save(usuarioEntity);
            emailService.enviarEmail(email, contrasenia); 
    }

    public String generarcontrasenia(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    public UsuarioEntity buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email).get();
    }

    @Override
    public Optional<List<UsuarioEntity>> listarEvaluadores() {
        List<UsuarioEntity> evaluadores = usuarioRepository.findByRol(new RolEntity(2, "ROL_EVALUADOR"));
        return Optional.ofNullable(evaluadores.isEmpty() ? null : evaluadores);
    }

    @Override
    public void inhabilitarEvaluador(Integer id) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(id);
        if(!usuarioOptional.isPresent()){
            throw new UsuarioException("No hay un evaluador registrado con el id" + id);
        }
        UsuarioEntity evaluador = usuarioOptional.get();
        evaluador.setEstado(!evaluador.getEstado());
        usuarioRepository.save(evaluador);
    }
}
