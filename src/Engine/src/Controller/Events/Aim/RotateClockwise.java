package Engine.src.Controller.Events.Aim;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class RotateClockwise extends AimModifierEvent{

    public RotateClockwise(Map<String, EngineInstance> engineInstanceSet){
        super(engineInstanceSet);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        rotateAim(engineInstance, true);
    }
}
