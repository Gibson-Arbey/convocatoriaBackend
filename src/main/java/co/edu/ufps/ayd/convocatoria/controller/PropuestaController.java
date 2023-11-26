package co.edu.ufps.ayd.convocatoria.controller;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import co.edu.ufps.ayd.convocatoria.service.implementations.ArchivoService;
import co.edu.ufps.ayd.convocatoria.service.implementations.ConvocatoriaService;
import co.edu.ufps.ayd.convocatoria.service.implementations.PropuestaService;
import co.edu.ufps.ayd.convocatoria.service.implementations.UsuarioService;

@RestController
@RequestMapping("/propuesta")
@Validated
public class PropuestaController {

    @Autowired
    ArchivoService archivoService;

    @Autowired
    ConvocatoriaService convocatoriaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PropuestaService propuestaService;

    @PostMapping("/guardarArchivo")
    @PreAuthorize("hasAuthority('ROL_PROPONENTE')")
    public ResponseEntity<String> subirArchivo(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            String archivo = archivoService.guardar(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("http://localhost:8080/propuesta/" + archivo);
        } catch (FileAlreadyExistsException e) {
            throw new IOException("El archivo ya existe en la ubicación especificada.", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> obtenerArchivo(@PathVariable String filename) throws Exception {
        Resource resource = archivoService.obtenerArchivo(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN', 'ROL_PROPONENTE')")
    public ResponseEntity<String> registrarPropuesta(@RequestBody List<Object> datos) {
        ConvocatoriaEntity convocatoriaEntity = convocatoriaService.buscarConvocatoriaHabilitada();
        PropuestaEntity propuesta  = new PropuestaEntity();
        if (convocatoriaEntity.getEstado()) {
            if (!datos.isEmpty()) {
                List<ProponenteEntity> proponentes = new ArrayList<>();
                for (Object dato : datos) {
                    if (dato instanceof Map) {
                        Map<?, ?> datoMap = (Map<?, ?>) dato;
                        if (datoMap.containsKey("tipo")) {
                            PropuestaEntity propuestaEntity = convertirAModeloPropuesta((Map<?, ?>) dato);
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            String username = authentication.getName();
                            UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioEmail(username);
                            propuestaEntity.setConvocatoria(convocatoriaEntity);
                            propuestaEntity.setUsuario(usuarioEntity);
                            propuesta = propuestaEntity;
                        } else if (datoMap.containsKey("cedula")) {
                            ProponenteEntity proponenteEntity = convertirAModeloProponente(datoMap);
                            proponentes.add(proponenteEntity);
                        }
                    }
                }
                propuestaService.registrarPropuesta(propuesta, proponentes);
                return ResponseEntity.ok().body("Propuesta registrada con exito");
            }
        }
        return ResponseEntity.badRequest().body("No hay una convocatoria abierta");
    }

    @GetMapping("/buscarRegistradas")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN', 'ROL_PROPONENTE')")
    public List<PropuestaEntity> listarPropuestasRegistradasDelUsuario(){
        ConvocatoriaEntity convocatoriaEntity = convocatoriaService.buscarConvocatoriaHabilitada();
        if (convocatoriaEntity == null) {
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return propuestaService.listarPropuestasRegistradasDelUsuario(email);
    }

    @GetMapping("/asignarEvaluador")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<String> asignarEvaluador(@RequestParam("idPropuesta") Integer idPropuesta, @RequestParam("idEvaluador") Integer idEvaluador){
        propuestaService.asignarEvaluador(idPropuesta, idEvaluador);
        return ResponseEntity.ok("Evaluador asignado exitosamente");
    }

    private PropuestaEntity convertirAModeloPropuesta(Map<?, ?> datoMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(datoMap, PropuestaEntity.class);
    }

    private ProponenteEntity convertirAModeloProponente(Map<?, ?> datoMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(datoMap, ProponenteEntity.class);
    }
}
