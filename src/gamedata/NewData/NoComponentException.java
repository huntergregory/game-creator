package gamedata.NewData;

import EngineException;

public class NoComponentException extends EngineException {
    private static final String MESSAGE = "Entity doesn't contain component.";

    public NoComponentException(String error) {
        super(MESSAGE, error);
    }
}
