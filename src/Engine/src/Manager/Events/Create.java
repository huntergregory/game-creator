package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class Create extends Event {

    public Create(Set<EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        myEngineInstances.add(engineInstance);
    }
}
