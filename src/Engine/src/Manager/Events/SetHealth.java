package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetHealth extends HealthModifierEvent {
    public SetHealth(Set<Instance> instances) {
        super(instances, Integer.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
