package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.MotionComponent;

public abstract class MotionEvent extends ComponentDependentEvent {
    public MotionEvent(Game game, Class<?>... parameterTypes) {
        super(game, MotionComponent.class, parameterTypes);
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
    }

    public void setX(int obj, double newX){
        BasicComponent basic = getComponent(obj, BasicComponent.class);
        double currentX = basic.getX();
        double finalX = newX;
        CollisionDetector collisionDetector = new CollisionDetector(this);
        Integer[] impassableColliders = collisionDetector.getImpassableColliders(obj, myEntityMap.keySet());
        for(Integer impassable : impassableColliders){
            if ((collisionDetector.collideFromLeft(impassable, obj) && newX < currentX) ||
                    (collisionDetector.collideFromLeft(obj, impassable) && newX > currentX)) {
                finalX = currentX;
            }
        }
        basic.setX(finalX);
    }

    //TODO remove duplication between setY and also in collision handler and detector
    public void setY(int obj, double newY){
        BasicComponent basic = getComponent(obj, BasicComponent.class);
        double currentY = basic.getY();
        double finalY = newY;
        CollisionDetector collisionDetector = new CollisionDetector(this);
        Integer[] impassableColliders = collisionDetector.getImpassableColliders(obj, myEntityMap.keySet());
        for(Integer impassable : impassableColliders){
            if ((collisionDetector.collideFromTop(impassable, obj) && newY < currentY) ||
                    (collisionDetector.collideFromTop(obj, impassable) && newY > currentY)) {
                finalY = currentY;
            }
        }
        basic.setY(finalY);
    }
*/
}
