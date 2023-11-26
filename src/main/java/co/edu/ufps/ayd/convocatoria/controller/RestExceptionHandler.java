package co.edu.ufps.ayd.convocatoria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import co.edu.ufps.ayd.convocatoria.exception.CodigoExistsException;
import co.edu.ufps.ayd.convocatoria.exception.CodigoInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.ContraseniaInvalidException;
import co.edu.ufps.ayd.convocatoria.exception.ConvocatoriaException;
import co.edu.ufps.ayd.convocatoria.exception.EmailExistsException;
import co.edu.ufps.ayd.convocatoria.exception.EmailInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.MateriaException;
import co.edu.ufps.ayd.convocatoria.exception.ProfesorException;
import co.edu.ufps.ayd.convocatoria.exception.PropuestaException;
import co.edu.ufps.ayd.convocatoria.exception.SemilleroException;
import co.edu.ufps.ayd.convocatoria.exception.UsuarioException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {EmailExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleEmailExistsException(EmailExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya existe.");
    }

    @ExceptionHandler(value = {CodigoInvalidoException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleCodigoInvalidoException(CodigoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El codigo no es valido.");
    }

    @ExceptionHandler(value = {EmailInvalidoException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleEmailInvalidoException(EmailInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El codigo no es valido.");
    }

    @ExceptionHandler(value = {CodigoExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleCodigoExistsException(CodigoExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El codigo ya esta registrado.");
    }

    @ExceptionHandler(value = {ContraseniaInvalidException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleContraseniaInvalidException(ContraseniaInvalidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("La contraseña debe tener minimo 8 caracteres.");
    }

    @ExceptionHandler(value = {ConvocatoriaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConvocatoriaException(ConvocatoriaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {MateriaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMateriaException(MateriaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {ProfesorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleProfesorException(ProfesorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {SemilleroException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleSemilleroException(SemilleroException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verifica el tamaño del archivo");
	}

    @ExceptionHandler(value = {PropuestaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePropuestaException(PropuestaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {UsuarioException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUsuarioException(UsuarioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}