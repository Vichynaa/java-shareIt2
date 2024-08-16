package exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String m) {
        super(m);
    }
}