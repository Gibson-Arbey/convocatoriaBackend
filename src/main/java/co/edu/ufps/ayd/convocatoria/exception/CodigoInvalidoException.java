package co.edu.ufps.ayd.convocatoria.exception;

public class CodigoInvalidoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public CodigoInvalidoException(String message) {
        super(message);
    }
}
