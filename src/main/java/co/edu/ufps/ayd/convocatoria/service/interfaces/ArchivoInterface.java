package co.edu.ufps.ayd.convocatoria.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoInterface {

    public void guardar(MultipartFile archivo) throws Exception;

    public Resource obtenerArchivo(String nombre) throws Exception; 

}
