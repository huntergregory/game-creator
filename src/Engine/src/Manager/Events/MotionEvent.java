package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class MotionEvent extends ComponentDependentEvent {
    public MotionEvent(Set<Instance> instances, Class<?> parameterTypes) {
        super(instances, MotionComponent.class, parameterTypes);
    }

/*    private double getAngle() {
        return Math.toDegrees(Math.tan(myMovementYVelocity / myMovementXVelocity));
    }

    public double getVelocity(){
        return Math.pow(Math.pow(myXVelocity, 2) + Math.pow(myYVelocity, 2), .5);
    }

    public double getMovementVelocity(){
        return Math.pow(Math.pow(myMovementXVelocity, 2) + Math.pow(myMovementYVelocity, 2), .5);
    }

    //Should put the three methods below into entitymanager?
    public void updateVelocity() {
        myXVelocity = Math.min(myXVelocity += myXAcceleration, myMaxXVelocity);
        myYVelocity = Math.min(myYVelocity += myYAcceleration, myMaxYVelocity);
    }

    public double getNewX(double x) {
        return x + myXVelocity;
    }

    public double getNewY(double y) {
        return y + myYVelocity;
    }

    public void adjustDirection(double delta) {
        myAngle += delta;
        adjustVelocitiesByAngle(myAngle);
    }

    public void setDirection(double angle) {
        myAngle = angle;
        adjustVelocitiesByAngle(myAngle);
    }

    private void adjustVelocitiesByAngle(double angle) {
        double[] directionVec = calculateDirection(myAngle);
        double totalVel = myXVelocity*myXVelocity + myYVelocity*myYVelocity;
        myXVelocity = totalVel * directionVec[0];
        myYVelocity = totalVel * directionVec[1];
    }

    private double[] calculateDirection(double angle){
        double[] directionVec = new double[2];
        directionVec[0] = Math.cos(Math.toRadians(angle));
        directionVec[1] = Math.sin(Math.toRadians(angle));
        return directionVec;
    }*/
}
