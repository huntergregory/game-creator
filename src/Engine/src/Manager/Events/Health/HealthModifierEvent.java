package Engine.src.Manager.Events.Health;

<<<<<<< HEAD:src/Engine/src/Manager/Events/Health/HealthModifierEvent.java
import Engine.src.Manager.Events.ComponentDependentEvent;
import gamedata.Game;
=======
>>>>>>> 04d14a832ce0911434cbe87621ab3fba2d05e9cb:src/Engine/src/Manager/Events/HealthModifierEvent.java
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
