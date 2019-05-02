package Engine.src.Controller.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class Create extends Event {

    public Create(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects);
    }

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        myEngineInstances.put(engineInstance.getID(), engineInstance);
    }
}
