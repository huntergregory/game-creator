package Engine.src.Controller;

public class NoInstanceException extends RuntimeException {
    private static final String MESSAGE = "Can't find instance with id ";

    public NoInstanceException(String id) {
        super(MESSAGE + id);
    }
}
