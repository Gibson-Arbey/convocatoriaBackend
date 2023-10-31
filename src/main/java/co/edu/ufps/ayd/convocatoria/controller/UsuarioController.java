package co.edu.ufps.ayd.convocatoria.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.ayd.convocatoria.model.dto.UsuarioDTO;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @PostMapping("/guardarEvaluador")
    public ResponseEntity<String> guardarEvaluador(@RequestBody UsuarioDTO usuarioDTO) {
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
    public ResponseEntity<String> guardarProponente(@RequestBody UsuarioDTO usuarioDTO) {
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

    @GetMapping("/prueba")
    public String prueba(){
        return "estas autorizado :)";
    }

    @GetMapping("/prueba2")
    public String prueba2(){
        return "estas autorizado :)";
    }
}
