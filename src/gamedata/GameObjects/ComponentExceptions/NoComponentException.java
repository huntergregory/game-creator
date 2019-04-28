package gamedata.GameObjects.ComponentExceptions;


public class NoComponentException extends ComponentException {
    private static final String MESSAGE = " doesn't contain component.";

    public NoComponentException(String instanceID) {
        super(instanceID + MESSAGE);
    }
}
