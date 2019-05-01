package Engine.src.Manager.Events.Health;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.HealthComponent;

import java.util.Map;
import java.util.Set;

public class AddToHealth extends HealthModifierEvent {
    public AddToHealth(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, Integer.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var healthComponent = engineInstance.getComponent(HealthComponent.class);
        int health = healthComponent.getHealth();
        setHealth(engineInstance, (int) args[0] + health);
    }
}
