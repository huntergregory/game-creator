package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class Create extends Event {

    public Create(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void execute(Instance instance, Object... args) {
        myInstances.add(instance);
    }
}
