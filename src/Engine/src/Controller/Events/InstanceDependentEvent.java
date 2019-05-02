package Engine.src.Controller.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public abstract class InstanceDependentEvent extends Event {
    public InstanceDependentEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<?>... parameterTypes) {
        super(engineInstances, engineObjects, parameterTypes);
    }

    protected abstract void modifyInstance(EngineInstance engineInstance, double stepTime, Object ... args);

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        if (!myEngineInstances.containsKey(engineInstance.getID()))
            return;
        modifyInstance(engineInstance, stepTime, args);
    }
}
