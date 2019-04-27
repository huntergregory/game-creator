package gamedata.GameObjects;

public class BasicComponentException extends ComponentException {
    private static final String MESSAGE = "Basic Component can't be removed.";

    BasicComponentException() {
        super(MESSAGE);
    }
}
