package Engine.src.EngineData.ComponentExceptions;

/**
 * Thrown when one tries to remove a BasicComponent
 * @author Hunter Gregory
 */
public class BasicComponentException extends ComponentException {
    private static final String MESSAGE = "Basic Component can't be removed.";

    public BasicComponentException() {
        super(MESSAGE);
    }
}
