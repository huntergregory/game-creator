package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class UpdateVelocity extends MotionEvent {
    public UpdateVelocity(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void modifyComponents(Instance instance, ) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        var environmentComponent = motionComponent.getEnvironmentImmersedIn();
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        double xAcc = environmentComponent.getAccelX();
        double yAcc = environmentComponent.getAccelY();
        double maxXVel = environmentComponent.getMaxXVelocity();
        double maxYVel = environmentComponent.getMaxYVelocity();
        motionComponent.setXVelocity(Math.min(xVel + xAcc, maxXVel));
        motionComponent.setYVelocity(Math.min(yVel + yAcc, maxYVel));
    }
}
