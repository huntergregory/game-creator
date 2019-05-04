package Engine.src.EngineData;

public class UnmodifiableEngineGameObject {

    private EngineGameObject myEngineGameObject;

    public UnmodifiableEngineGameObject(EngineGameObject engineGameObject) {
        myEngineGameObject = engineGameObject;
    }

    public EngineInstance createInstance(String id) {
        return myEngineGameObject.createInstance(id);
    }

    public String getID() {
        return myEngineGameObject.getID();
    }
}
