package co.edu.ufps.ayd.convocatoria.exception;

public class ContraseniaInvalidException  extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ContraseniaInvalidException(String message) {
        super(message);
    }
}
