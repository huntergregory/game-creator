package Engine.src.Controller.Events.Health;

import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.HealthComponent;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public abstract class HealthModifierEvent extends ComponentDependentEvent {

    public HealthModifierEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<?> parameterTypes) {
        super(engineInstances, engineObjects, HealthComponent.class, parameterTypes);
    }

    protected void setHealth(EngineInstance engineInstance, int health) {
        var healthComponent = engineInstance.getComponent(HealthComponent.class);
        int maxHealth = healthComponent.getMaxHealth();
        if (health > maxHealth)
            health = maxHealth;
        healthComponent.setHealth(health);
    }
}
