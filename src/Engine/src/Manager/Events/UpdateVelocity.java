package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

public class UpdateVelocity extends MotionEvent {
    public UpdateVelocity(Game game) {
        super(game);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
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
