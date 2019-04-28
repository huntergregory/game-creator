package Engine.src.Manager.Events.Health;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class AddToHealth extends HealthModifierEvent {
    public AddToHealth(Set<Instance> instances) {
        super(instances, Integer.class);
    }

    //TODO: This sets the health, but doesn't add to it???
    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
