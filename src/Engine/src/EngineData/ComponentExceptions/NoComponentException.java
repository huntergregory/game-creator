package Engine.src.EngineData.ComponentExceptions;

/**
 * Thrown when you try to get a component which the container doesn't have.
 * @author Hunter Gregory
 */
public class NoComponentException extends ComponentException {
    private static final String MESSAGE = " doesn't contain component.";

    public NoComponentException(String instanceID) {
        super(instanceID + MESSAGE);
    }
}
