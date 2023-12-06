package co.edu.ufps.ayd.convocatoria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.ayd.convocatoria.exception.UsuarioException;
import co.edu.ufps.ayd.convocatoria.model.dto.UsuarioDTO;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/guardarEvaluador")
    public ResponseEntity<String> guardarEvaluador(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            BeanUtils.copyProperties(usuarioDTO, usuarioEntity);
            usuarioEntity.setContrasenia(passwordEncoder.encode(usuarioDTO.getContraseniaDesencriptada()));

            usuarioService.guardarUsuario(usuarioEntity, "ROL_EVALUADOR");
            String response = "Evaluador registrado con exito";
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/guardarProponente")
    public ResponseEntity<String> guardarProponente(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            BeanUtils.copyProperties(usuarioDTO, usuarioEntity);
            usuarioEntity.setContrasenia(passwordEncoder.encode(usuarioDTO.getContraseniaDesencriptada()));
            usuarioService.guardarUsuario(usuarioEntity, "ROL_PROPONENTE");
            String response = "Proponente registrado con exito";
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/reestablecerContrasenia")
    public ResponseEntity<String> reestablecerContrasenia(@RequestParam String email) {
        try {
            String nuevaContrasenia = usuarioService.generarcontrasenia(8);
            String contraseniaEncriptada = passwordEncoder.encode(nuevaContrasenia);
            usuarioService.reestablecerContrasenia(email, nuevaContrasenia, contraseniaEncriptada);
            return ResponseEntity.ok().body("Correo enviado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al reestablecer la contraseña: " + e.getMessage());
        }
    }

    @GetMapping("/listarEvaluadores")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public List<UsuarioEntity> listarEvaluadores() {
        Optional<List<UsuarioEntity>> evaluadoresOptional = usuarioService.listarEvaluadores();

        return evaluadoresOptional.orElseThrow(() -> new UsuarioException("No hay evaluadores registrados"));
    }

    @GetMapping("/inhabilitarEvaluador")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> inhabilitarEvaluador(@RequestParam("id") Integer id) {
        try {
            usuarioService.inhabilitarEvaluador(id);
            return ResponseEntity.ok("Evaluador (in)habilitado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

}
