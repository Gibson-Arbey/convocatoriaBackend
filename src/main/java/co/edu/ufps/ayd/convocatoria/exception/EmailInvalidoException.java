package co.edu.ufps.ayd.convocatoria.exception;

public class EmailInvalidoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public EmailInvalidoException(String message) {
        super(message);
    }
}
