package gamedata.Instance;

import EngineException;

public class NoEntityException extends EngineException {
    public NoEntityException(String error){
        super("NoEntity Error", error);
    }
}
