package co.edu.ufps.ayd.convocatoria.exception;

public class CodigoExistsException  extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public CodigoExistsException(String message) {
        super(message);
    }
}
