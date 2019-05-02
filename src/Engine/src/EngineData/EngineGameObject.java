package Engine.src.EngineData;

import Engine.src.Controller.NoInstanceException;
import Engine.src.Controller.NoObjectException;

public class EngineGameObject extends ComponentContainer implements Cloneable {

    public EngineGameObject(String id) {
        super(id);
    }

    public EngineInstance createInstance(String id) throws NoInstanceException {
        try {
            var copy = (EngineGameObject) this.clone();
            return new EngineInstance(id, getID(), copy.getComponents());
        }
        catch (CloneNotSupportedException e) {
            throw new NoObjectException(this.getID() + ". Can't clone Engine Game Object.");
        }
    }
}
