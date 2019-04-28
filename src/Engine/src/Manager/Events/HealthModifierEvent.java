package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class HealthModifierEvent extends ComponentDependentEvent {

    public HealthModifierEvent(Set<Instance> instances, Class<?> parameterTypes) {
        super(instances, HealthComponent.class, parameterTypes);
    }

    protected void setHealth(Instance instance, int health) {
        var healthComponent = instance.getComponent(HealthComponent.class);
        int maxHealth = healthComponent.getMaxHealth();
        if (health > maxHealth)
            health = maxHealth;
        healthComponent.setHealth(health);
    }
}
