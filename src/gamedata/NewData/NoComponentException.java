package gamedata.NewData;


public class NoComponentException extends RuntimeException {
    private static final String MESSAGE = " doesn't contain component.";

    public NoComponentException(String instanceID) {
        super(instanceID + MESSAGE);
    }
}
