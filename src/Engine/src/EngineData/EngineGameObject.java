package Engine.src.EngineData;

import Engine.src.Controller.NoInstanceException;

public class EngineGameObject extends ComponentContainer implements Cloneable {

    public EngineGameObject(String id) {
        super(id);
    }

    public EngineInstance createInstance(String id) throws NoInstanceException {
            return new EngineInstance(id, getID(), copyComponents());
    }
}
