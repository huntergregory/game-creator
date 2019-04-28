package gamedata.GameObjects.ComponentExceptions;

public class BasicComponentException extends ComponentException {
    private static final String MESSAGE = "Basic Component can't be removed.";

    public BasicComponentException() {
        super(MESSAGE);
    }
}
