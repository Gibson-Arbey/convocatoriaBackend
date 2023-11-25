package co.edu.ufps.ayd.convocatoria.controller;


import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import co.edu.ufps.ayd.convocatoria.service.implementations.ArchivoService;

@RestController
@RequestMapping("/propuesta")
@Validated
public class PropuestaController {
    
    @Autowired
    ArchivoService archivoService;

    @PostMapping("/guardarArchivo")
    @PreAuthorize("hasAuthority('ROL_PROPONENTE')")
	public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile file) throws Exception {
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
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
		Resource resource = archivoService.obtenerArchivo(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
