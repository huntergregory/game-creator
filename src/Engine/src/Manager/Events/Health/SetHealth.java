package Engine.src.Manager.Events.Health;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class SetHealth extends HealthModifierEvent {
    public SetHealth(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, Integer.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        setHealth(engineInstance, (int) args[0]);
    }
}
