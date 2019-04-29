package Player.Features.ScrollableWindows;

public class IncompatibleArgumentLengthException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Length of values array must be the same length as the names array.";

    public IncompatibleArgumentLengthException() {
        super(ERROR_MESSAGE);
    }
}
