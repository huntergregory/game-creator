package Engine.src.Manager.Events.Health;

import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class AddToHealth extends HealthModifierEvent {
    public AddToHealth(Set<Instance> instances) {
        super(instances, Integer.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var healthComponent = instance.getComponent(HealthComponent.class);
        int health = healthComponent.getHealth();
        setHealth(instance, (int) args[0] + health);
    }
}
