package co.edu.ufps.ayd.convocatoria.service.implementations;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ufps.ayd.convocatoria.service.interfaces.ArchivoInterface;

@Service
public class ArchivoService implements ArchivoInterface {

    private final Path ruta = Paths.get("archivos");
    @Override
    public String guardar(MultipartFile archivo) throws Exception {
        Files.copy(archivo.getInputStream(), this.ruta.resolve(archivo.getOriginalFilename()));
        return archivo.getOriginalFilename();
    }

    @Override
    public Resource obtenerArchivo(String nombre) throws Exception{
        Path archivo = ruta.resolve(nombre);
		Resource resource = new UrlResource(archivo.toUri());
		return resource;
    }
    
}
