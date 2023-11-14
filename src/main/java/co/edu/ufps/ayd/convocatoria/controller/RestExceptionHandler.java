package co.edu.ufps.ayd.convocatoria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.ufps.ayd.convocatoria.exception.CodigoExistsException;
import co.edu.ufps.ayd.convocatoria.exception.CodigoInvalidoException;
import co.edu.ufps.ayd.convocatoria.exception.ContraseniaInvalidException;
import co.edu.ufps.ayd.convocatoria.exception.EmailExistsException;
import co.edu.ufps.ayd.convocatoria.exception.EmailInvalidoException;

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
}