package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Instance;

public abstract class HealthModifierEvent extends ComponentDependentEvent {

    public HealthModifierEvent(Game game, Class<?> parameterTypes) {
        super(game, HealthComponent.class, parameterTypes);
    }

    protected void setHealth(Instance instance, int health) {
        if (!instance.hasComponent(HealthComponent.class))
            return;
        var healthComponent = instance.getComponent(HealthComponent.class);
        int maxHealth = healthComponent.getMaxHealth();
        if (health > maxHealth)
            health = maxHealth;
        healthComponent.setHealth(health);
    }
}
