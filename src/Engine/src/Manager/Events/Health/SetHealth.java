package Engine.src.Manager.Events.Health;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class SetHealth extends HealthModifierEvent {
    public SetHealth(Set<EngineInstance> engineInstances) {
        super(engineInstances, Integer.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        setHealth(engineInstance, (int) args[0]);
    }
}
