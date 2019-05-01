package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public abstract class InstanceDependentEvent extends Event {
    public InstanceDependentEvent(Set<EngineInstance> engineInstances, Class<?>... parameterTypes) {
        super(engineInstances, parameterTypes);
    }

    protected abstract void modifyInstance(EngineInstance engineInstance, double stepTime, Object ... args);

    @Override
    protected void execute(EngineInstance engineInstance, double stepTime, Object... args) {
        if (!myEngineInstances.contains(engineInstance))
            return;
        modifyInstance(engineInstance, stepTime, args);
    }
}
