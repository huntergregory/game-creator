package Engine.src.Controller.Events.Motion;

import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public abstract class MotionEvent extends ComponentDependentEvent {
    public MotionEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<? extends Component>[] componentClasses, Class<?>... parameterTypes) {
        super(engineInstances, engineObjects, componentClasses, parameterTypes);
    }

    protected double getAngle(EngineInstance engineInstance) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double movementYVel = motionComponent.getMovementYVelocity();
        double movementXVel = motionComponent.getMovementXVelocity();
        return Math.toDegrees(Math.tan(movementYVel / movementXVel));
    }

    protected double getMovementVelocity(EngineInstance engineInstance) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double movementYVel = motionComponent.getMovementYVelocity();
        double movementXVel = motionComponent.getMovementXVelocity();
        return Math.pow(Math.pow(movementXVel, 2) + Math.pow(movementYVel, 2), .5);
    }

    protected double getVelocity(EngineInstance engineInstance) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        double yVel = motionComponent.getYVelocity();
        return Math.pow(Math.pow(xVel, 2) + Math.pow(yVel, 2), .5);
    }

    protected double getNewX(EngineInstance engineInstance, double x, double stepTime) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double xVel = motionComponent.getXVelocity();
        return x + xVel * stepTime;
    }

    protected double getNewY(EngineInstance engineInstance, double y, double stepTime) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double yVel = motionComponent.getYVelocity();
        return y + yVel * stepTime;
    }

    protected void adjustVelocitiesByAngle(EngineInstance engineInstance, double angle) {
        double[] directionVec = calculateDirection(angle);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
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
