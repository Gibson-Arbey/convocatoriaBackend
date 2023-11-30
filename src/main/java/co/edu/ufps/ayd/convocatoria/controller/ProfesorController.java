package co.edu.ufps.ayd.convocatoria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import co.edu.ufps.ayd.convocatoria.model.entity.ProfesorEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.ProfesorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/profesor")
@Validated
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public List<ProfesorEntity> listarProfesoresRegistrados(){
        try {
            return profesorService.listarProfesores();
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/listarActivos")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN', 'ROL_PROPONENTE')")
    public List<ProfesorEntity> listarProfesoresActivos(){
        try {
            return profesorService.listarProfesoresActivos();
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/agregar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> agregarPrfoesor(@Valid @RequestBody ProfesorEntity profesorEntity){
        try {
            profesorService.agregarProfesor(profesorEntity);
            return ResponseEntity.ok("Profesor guardado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/obtenerInfo")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<ProfesorEntity> obtenerInfo(@RequestParam("id") Integer id){
        try {
            return new ResponseEntity<>(profesorService.buscarProfesor(id), HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> modificarProfesor(@RequestParam("id") Integer id, @Valid @RequestBody ProfesorEntity profesorEntity){
        try {
            profesorService.modificarProfesor(id, profesorEntity);
            return ResponseEntity.ok("Profesor modificado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/inhabilitar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> inhabilitarProfesor(@RequestParam("id") Integer id) {
        try {
            profesorService.inhabilitarProfesor(id);
            return ResponseEntity.ok("Profesor (in)habilitado exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }
}
