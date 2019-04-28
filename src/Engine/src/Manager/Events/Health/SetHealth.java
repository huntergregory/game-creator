package Engine.src.Manager.Events.Health;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetHealth extends HealthModifierEvent {
    public SetHealth(Set<Instance> instances) {
        super(instances, Integer.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
