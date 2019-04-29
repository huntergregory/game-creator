package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class InstanceDependentEvent extends Event {
    public InstanceDependentEvent(Set<Instance> instances, Class<?>... parameterTypes) {
        super(instances, parameterTypes);
    }

    protected abstract void modifyInstance(Instance instance, Object ... args);

    @Override
    protected void execute(Instance instance, Object... args) {
        if (!myInstances.contains(instance))
            return;
        modifyInstance(instance, args);
    }
}
