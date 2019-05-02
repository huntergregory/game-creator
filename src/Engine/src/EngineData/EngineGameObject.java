package Engine.src.EngineData;

public class EngineGameObject extends ComponentContainer {

    public EngineGameObject(String id) {
        super(id);
    }

    public EngineInstance createInstance(String id) {
        return new EngineInstance(id, getID(), getComponents());
    }

}
