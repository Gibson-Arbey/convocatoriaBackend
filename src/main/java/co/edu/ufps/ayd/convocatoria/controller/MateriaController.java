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

import co.edu.ufps.ayd.convocatoria.model.entity.MateriaEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.MateriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/materia")
@Validated
public class MateriaController {
    
    @Autowired
    MateriaService materiaService;

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public List<MateriaEntity> listarMateriasRegistradas(){
        try {
            return materiaService.listarMaterias();
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/listarActivas")
    @PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_PROPONENTE')")
    public List<MateriaEntity> listarMateriasactivas(){
        try {
            return materiaService.listarMateriasActivas();
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/agregar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> agregarMateria(@Valid @RequestBody MateriaEntity materiaEntity){
        try {
            materiaService.agregarMateria(materiaEntity);
            return ResponseEntity.ok("Materia guardada exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> modificarMateria(@RequestParam("id") Integer id, @Valid @RequestBody MateriaEntity materiaEntity){
        try {
            materiaService.modificarMateria(id, materiaEntity);
            return ResponseEntity.ok("Materia modificada exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/inhabilitar")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> inhabilitarMateria(@RequestParam("id") Integer id) {
        try {
            materiaService.inhabilitarMateria(id);
            return ResponseEntity.ok("Materia (in)habilitada exitosamente");
        } catch (Exception e) {
            throw e;
        }
    }
}
