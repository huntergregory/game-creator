package Engine.src.Manager.Events.Health;

import Engine.src.EngineData.EngineInstance;
import Engine.src.Manager.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.HealthComponent;

import java.util.Set;

public abstract class HealthModifierEvent extends ComponentDependentEvent {

    public HealthModifierEvent(Set<EngineInstance> engineInstances, Class<?> parameterTypes) {
        super(engineInstances, HealthComponent.class, parameterTypes);
    }

    protected void setHealth(EngineInstance engineInstance, int health) {
        var healthComponent = engineInstance.getComponent(HealthComponent.class);
        int maxHealth = healthComponent.getMaxHealth();
        if (health > maxHealth)
            health = maxHealth;
        healthComponent.setHealth(health);
    }
}
