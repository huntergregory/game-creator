package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class SetDirection extends MotionEvent {
    public SetDirection(Set<EngineInstance> engineInstances) {
        super(engineInstances, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        double angle = (double) args[0];
        adjustVelocitiesByAngle(engineInstance, angle);
    }
}
