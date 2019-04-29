package Engine.src.Manager.Events.Motion;

import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class UpdateVelocity extends MotionEvent {
    public UpdateVelocity(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        double xAcc = motionComponent.getXAccel();
        double yAcc = motionComponent.getYAccel();
        motionComponent.setXVelocity(xVel + xAcc);
        motionComponent.setYVelocity(yVel + yAcc);
    }
}
