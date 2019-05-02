package Engine.src.Manager.Events.Health;

import Engine.src.EngineData.Components.HealthComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Manager.Events.ComponentDependentEvent;

import java.util.Map;

public abstract class HealthModifierEvent extends ComponentDependentEvent {

    public HealthModifierEvent(Map<String, EngineInstance> engineInstances, Class<?> parameterTypes) {
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
