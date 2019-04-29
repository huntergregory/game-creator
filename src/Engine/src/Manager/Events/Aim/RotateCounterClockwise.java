package Engine.src.Manager.Events.Aim;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class RotateCounterClockwise extends AimModifierEvent {
    public RotateCounterClockwise(Set<EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object... args) {
        rotateAim(engineInstance, false);
    }
}
