package gamedata.NewData;

import EngineException;

public class NoEntityException extends EngineException {
    public NoEntityException(String error){
        super("NoEntity Error", error);
    }
}
