package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class AddToHealth extends HealthModifierEvent {
    public AddToHealth(Set<Instance> instances) {
        super(instances, Integer.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
