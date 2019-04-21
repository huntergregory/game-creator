package Engine.src.ECS;

import Engine.src.Components.AimComponent;
import Engine.src.Components.BasicComponent;
import Engine.src.Components.LOSComponent;
import Engine.src.Components.MotionComponent;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class AI {

    private EntityManager myEntityManager;
    private final double myCorrectionAngle = 2;
    private final double myCorrectionDistance = 150;

    public AI(EntityManager entityManager) {
        myEntityManager = entityManager;
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

    public void flee(int referenceID, int targetID){
        movementResponse(referenceID, targetID, "FLEE");
    }

    public void follow(int referenceID, int targetID) {
        movementResponse(referenceID, targetID, "FOLLOW");
    }


    public void patrol(int entityID, ArrayList<Point2D> patrolRoute) {
        int patrolStage = findPatrolStage(entityID, patrolRoute);
        BasicComponent basic = myEntityManager.getComponent(entityID, BasicComponent.class);
        double[] distance = findDistanceVector(new Point2D.Double(basic.getX(), basic.getY()), patrolRoute.get(patrolStage));
        myEntityManager.moveInDirection(entityID, findDirection(distance));
    }

    private int findPatrolStage(int entityID, ArrayList<Point2D> patrolRoute) {
        BasicComponent basic = myEntityManager.getComponent(entityID, BasicComponent.class);
        double[] topLeftCorner = {basic.getX(), basic.getY()};
        double[] bottomRightCorner = {basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight()};
        Point2D earlierPoint;
        Point2D nextPoint;

        for (int currentPatrolPathIndex = 0; currentPatrolPathIndex < patrolRoute.size(); currentPatrolPathIndex++) {
            earlierPoint = patrolRoute.get(currentPatrolPathIndex);
            nextPoint = patrolRoute.get(currentPatrolPathIndex + 1);
            if (Line2D.linesIntersect(earlierPoint.getX(), earlierPoint.getY(), nextPoint.getX(), nextPoint.getY(),
                    topLeftCorner[0], topLeftCorner[1], bottomRightCorner[0], bottomRightCorner[1])) {
                return currentPatrolPathIndex + 1;
            }
        }
        return 0;
    }

    private boolean isInLOS(int targetID, int referenceID, double distance, double LOS) {
        return (LOS > distance && !myEntityManager.targetEntityObscured(targetID, referenceID));
    }

    private double[] findDistanceVector(int referenceID, int targetID) {
        BasicComponent referenceBasic = myEntityManager.getComponent(referenceID, BasicComponent.class);
        BasicComponent targetBasic = myEntityManager.getComponent(targetID, BasicComponent.class);
        double deltaX = targetBasic.getX() - referenceBasic.getX();
        double deltaY = targetBasic.getY() - referenceBasic.getY();
        double[] vector = {deltaX, deltaY};
        return vector;
    }

    private double[] findDistanceVector(Point2D reference, Point2D target) {
        double deltaX = reference.getX() - reference.getX();
        double deltaY = target.getY() - target.getY();
        double[] vector = {deltaX, deltaY};
        return vector;
    }

    private double[] findDirection(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        double magnitude = calculateMagnitude(vector);
        double[] direction = {deltaX / magnitude, deltaY / magnitude};
        return direction;
    }

    private double calculateMagnitude(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        return Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), .5);
    }

    public void baseAim(int shooterID, int targetID, double accuracy){
        double[] distanceVec = findDistanceVector(shooterID, targetID);
        double angle = Math.atan(distanceVec[1] / distanceVec[0]);
        aim(shooterID, angle, accuracy);
    }

    public void goodAim(int shooterID, int targetID, double accuracy){
        MotionComponent motion = myEntityManager.getComponent(targetID, MotionComponent.class);
        if (motion == null) baseAim(shooterID, targetID, accuracy);
        else {
            double xVel = motion.getXVelocity();
            double yVel = motion.getYVelocity();
            double[] distanceVec = findDistanceVector(shooterID, targetID);
            double idealX = distanceVec[0] + (myEntityManager.getStepTime() * xVel);
            double idealY = distanceVec[1] + (myEntityManager.getStepTime() * yVel);
            double[] idealDistanceVec = {idealX, idealY};
            double angle = Math.atan(idealDistanceVec[1] / idealDistanceVec[0]);
            aim(shooterID, angle, accuracy);
        }
    }

    private void aim(int shooterID, double angle, double accuracy){
        Random rand = new Random();
        angle += rand.nextGaussian() * angle * (1 - accuracy);
        AimComponent aim = myEntityManager.getComponent(shooterID, AimComponent.class);
        aim.setXAim(Math.cos(angle));
        aim.setYAim(Math.sin(angle));
    }
}