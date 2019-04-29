package Engine.src.Manager.Events.Aim;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class RotateClockwise extends AimModifierEvent{

    public RotateClockwise(Set<EngineInstance> engineInstanceSet){
        super(engineInstanceSet);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object... args) {
        rotateAim(engineInstance, true);
    }
}
