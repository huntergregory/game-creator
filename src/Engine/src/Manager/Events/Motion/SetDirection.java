package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class SetDirection extends MotionEvent {
    public SetDirection(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        double angle = (double) args[0];
        adjustVelocitiesByAngle(engineInstance, angle);
    }
}
