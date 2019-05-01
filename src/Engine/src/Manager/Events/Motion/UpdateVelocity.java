package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Map;
import java.util.Set;

public class UpdateVelocity extends MotionEvent {
    public UpdateVelocity(Map<String, EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        double xAcc = motionComponent.getXAccel();
        double yAcc = motionComponent.getYAccel();
        motionComponent.setXVelocity(xVel + xAcc);
        motionComponent.setYVelocity(yVel + yAcc);
    }
}
