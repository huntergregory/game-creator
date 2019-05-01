package Engine.src.Controller.Events.Aim;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class RotateCounterClockwise extends AimModifierEvent {
    public RotateCounterClockwise(Map<String, EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        rotateAim(engineInstance, false);
    }
}
