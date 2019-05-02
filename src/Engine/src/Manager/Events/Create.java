package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class Create extends Event {

    public Create(Map<String, EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        myEngineInstances.put(engineInstance.getID(), engineInstance);
    }
}
