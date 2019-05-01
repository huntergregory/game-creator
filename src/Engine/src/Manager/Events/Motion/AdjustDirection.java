package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class AdjustDirection extends MotionEvent {
    public AdjustDirection(Set<EngineInstance> engineInstances) {
        super(engineInstances, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        double angle = getAngle(engineInstance) + (double) args[0];
        adjustVelocitiesByAngle(engineInstance, angle);
    }
}
