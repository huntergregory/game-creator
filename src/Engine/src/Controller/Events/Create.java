package Engine.src.Controller.Events;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class Create extends Event {

    public Create(Map<String, EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        myEngineInstances.put(engineInstance.getID(), engineInstance);
    }
}
