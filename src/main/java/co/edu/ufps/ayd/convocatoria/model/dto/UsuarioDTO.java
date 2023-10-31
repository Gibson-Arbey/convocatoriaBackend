package co.edu.ufps.ayd.convocatoria.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    
    /**
     * El ID del usuario.
     */
    private Integer id;

    /**
     * El codigo del usuario ufps.
     */
    private String codigo;
    /**
     * El correo electrónico del usuario.
     */
    private String email;

    /**
     * La contraseña del usuario encriptada.
     */
    private String contrasenia;

    /**
     * La contraseña del usuario (sin encriptar).
     */
    private String contraseniaDesencriptada;
}
