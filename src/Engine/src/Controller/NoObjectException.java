package Engine.src.Controller;

public class NoObjectException extends RuntimeException {
    private static final String MESSAGE = "Can't find game object with name ";

    public NoObjectException(String objectType) {
        super(MESSAGE + objectType);
    }
}
