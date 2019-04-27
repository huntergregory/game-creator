package gamedata.NewData;


public class NoComponentException extends ComponentException {
    private static final String MESSAGE = " doesn't contain component.";

    NoComponentException(String instanceID) {
        super(instanceID + MESSAGE);
    }
}
