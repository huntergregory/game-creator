package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;
import java.util.Set;

public abstract class MotionEvent extends ComponentDependentEvent {
    public MotionEvent(Set<Instance> instances, Class<?> parameterTypes) {
        super(instances, MotionComponent.class, parameterTypes);
    }

    public MotionEvent(Set<Instance> instances, Class<? extends Component>[] componentClasses, Class<?>... parameterTypes) {
        super(instances, componentClasses, parameterTypes);
    }

    protected double getAngle(Instance instance) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double movementYVel = motionComponent.getMovementYVelocity();
        double movementXVel = motionComponent.getMovementXVelocity();
        return Math.toDegrees(Math.tan(movementYVel / movementXVel));
    }

    protected double getMovementVelocity(Instance instance) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double movementYVel = motionComponent.getMovementYVelocity();
        double movementXVel = motionComponent.getMovementXVelocity();
        return Math.pow(Math.pow(movementXVel, 2) + Math.pow(movementYVel, 2), .5);
    }

    protected double getVelocity(Instance instance) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        return Math.pow(Math.pow(xVel, 2) + Math.pow(yVel, 2), .5);
    }

    protected double getNewX(Instance instance, double x) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        return x + xVel;
    }

    protected double getNewY(Instance instance, double y) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double yVel = motionComponent.getYVelocity();
        return y + yVel;
    }

    protected void adjustVelocitiesByAngle(Instance instance, double angle) {
        double[] directionVec = calculateDirection(angle);
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        double totalVel = xVel*xVel + yVel*yVel;
        motionComponent.setXVelocity(totalVel * directionVec[0]);
        motionComponent.setYVelocity(totalVel * directionVec[1]);
    }

    private double[] calculateDirection(double angle){
        double[] directionVec = new double[2];
        directionVec[0] = Math.cos(Math.toRadians(angle));
        directionVec[1] = Math.sin(Math.toRadians(angle));
        return directionVec;
    }
}
