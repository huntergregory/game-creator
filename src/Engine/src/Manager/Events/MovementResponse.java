package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.LOSComponent;
import gamedata.GameObjects.Instance;

public class MovementResponse extends AIEvent{

    public MovementResponse(Game game) {
        super(game, Instance.class, String.class);
    }

    private void movementResponse(int referenceID, int targetID, String movementType) {
        LOSComponent LOSComp = myEntityManager.getComponent(referenceID, LOSComponent.class);
        double[] distanceVec = findDistanceVector(referenceID, targetID);
        double magnitude = calculateMagnitude(distanceVec);
        if (LOSComp == null || isInLOS(targetID, referenceID, magnitude, LOSComp.getLOS())) {
            double[] direction = findDirection(distanceVec);
            if(movementType.equals("FLEE")) {
                direction[0] = direction[0] * -1;
                direction[1] = direction[1] * -1;
                BasicComponent basic = myEntityManager.getComponent(referenceID, BasicComponent.class);
                double currentX = basic.getX();
                double currentY = basic.getY();
                double[] targetLocation = {currentX + (myCorrectionDistance * direction[0]), currentY + (myCorrectionDistance * direction[1])};
                while(myEntityManager.targetPointObscured(targetLocation[0], targetLocation[1], referenceID)){
                    double angle = Math.atan(currentY / currentX);
                    angle += myCorrectionAngle;
                    direction[0] = Math.cos(angle);
                    direction[1] = Math.sin(angle);
                    double[] newDirection = {currentX + (myCorrectionDistance * direction[0]), currentY + (myCorrectionDistance * direction[1])};
                    targetLocation = newDirection;
                }
            }
            myEntityManager.moveInDirection(referenceID, direction);
        }
    }
}
