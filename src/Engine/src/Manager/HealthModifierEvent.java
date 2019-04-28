package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Instance;

public abstract class HealthModifierEvent extends ComponentModifierEvent {

    public HealthModifierEvent(Game game, int numParameters) {
        super(game, numParameters, HealthComponent.class);
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
