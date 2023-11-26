package co.edu.ufps.ayd.convocatoria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.ayd.convocatoria.model.entity.SemilleroEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.SemilleroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/semillero")
@Validated
public class SemilleroController {
    
    @Autowired
    SemilleroService semilleroService;

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public List<SemilleroEntity> listarSemillerosRegistrados(){
        try {
            return semilleroService.listarSemilleros();
        } catch (Exception e) {
            throw e;
        }
    }
    @GetMapping("/listarActivos")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN', 'ROL_PROPONENTE')")
    public List<SemilleroEntity> listarSemillerosActivos(){
        try {
            return semilleroService.listarSemillerosActivos();
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/agregar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> agregarSemillero(@Valid @RequestBody SemilleroEntity semilleroEntity){
        try {
            semilleroService.agregarSemillero(semilleroEntity);
            return ResponseEntity.ok("Semillero guardado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> modificarSemillero(@RequestParam("id") Integer id, @Valid @RequestBody SemilleroEntity semilleroEntity){
        try {
            semilleroService.modificarSemillero(id, semilleroEntity);
            return ResponseEntity.ok("Semillero modificado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/inhabilitar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> inhabilitarSemillero(@RequestParam("id") Integer id) {
        try {
            semilleroService.inhabilitarSemillero(id);
            return ResponseEntity.ok("Semillero (in)habilitado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }
}
