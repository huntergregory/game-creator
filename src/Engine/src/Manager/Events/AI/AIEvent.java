package Engine.src.Manager.Events.AI;

import Engine.src.ECS.Line;
import Engine.src.ECS.Pair;
import Engine.src.Manager.Events.ComponentDependentEvent;
import gamedata.Game;
import gamedata.GameObjects.Components.*;
import gamedata.GameObjects.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AIEvent extends ComponentDependentEvent {

    private final double CORRECTION_ANGLE = 2;
    private final double CORRECTION_DISTANCE = 150;


    public AIEvent(Game game, Class<?>... parameterTypes) {
        super(game, BasicComponent.class, parameterTypes);
    }

    private boolean targetEntityObscured(Instance targetInstance, Instance referenceInstance) {
        BasicComponent targetBasic = targetInstance.getComponent(BasicComponent.class);
        double targetX = targetBasic.getX();
        double targetY = targetBasic.getY();
        return obscured(targetX, targetY, targetInstance.getID(), referenceInstance);
    }

    private boolean targetPointObscured(double targetLocationX, double targetLocationY, Instance referenceInstance){
        return obscured(targetLocationX, targetLocationY, "EMPTY", referenceInstance);
    }

    private boolean obscured(double targetLocationX, double targetLocationY, String targetID, Instance referenceInstance) {
        BasicComponent referenceBasic = referenceInstance.getComponent(BasicComponent.class);
        double referenceX = referenceBasic.getX();
        double referenceY = referenceBasic.getY();

        for (Instance instance : myInstances){
            String ID = instance.getID();
            if (!ID.equals(targetID) && !ID.equals(referenceInstance.getID())){
                BasicComponent basic = instance.getComponent(BasicComponent.class);
                Pair<Double> topLeftCorner = new Pair(basic.getX(), basic.getY());
                Pair<Double> bottomRightCorner = new Pair(basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight());

                Line line1 = new Line(targetLocationX, targetLocationY, referenceX, referenceY);
                Line line2 = new Line(topLeftCorner.getItem1(), topLeftCorner.getItem2(), bottomRightCorner.getItem1(), bottomRightCorner.getItem2());

                return line1.intersects(line2);
            }
        }

        return false;
    }

    private void movementResponse(Instance referenceInstance, Instance targetInstance, String movementType, double stepTime) {
        LOSComponent LOSComp = referenceInstance.getComponent(LOSComponent.class);
        double[] distanceVec = findDistanceVector(referenceInstance, targetInstance);
        double magnitude = calculateMagnitude(distanceVec);
        if (LOSComp == null || isInLOS(targetInstance, referenceInstance, magnitude, LOSComp.getLOS())) {
            double[] direction = findDirection(distanceVec);
            if(movementType.equals("FLEE")) {
                direction[0] = direction[0] * -1;
                direction[1] = direction[1] * -1;
                BasicComponent basic = referenceInstance.getComponent(BasicComponent.class);
                double currentX = basic.getX();
                double currentY = basic.getY();
                double[] targetLocation = {currentX + (CORRECTION_DISTANCE * direction[0]), currentY + (CORRECTION_DISTANCE * direction[1])};
                while(targetPointObscured(targetLocation[0], targetLocation[1], referenceInstance)){
                    double angle = Math.atan(currentY / currentX);
                    angle += CORRECTION_ANGLE;
                    direction[0] = Math.cos(angle);
                    direction[1] = Math.sin(angle);
                    double[] newDirection = {currentX + (CORRECTION_DISTANCE * direction[0]), currentY + (CORRECTION_DISTANCE * direction[1])};
                    targetLocation = newDirection;
                }
            }
            moveInDirection(referenceInstance, direction, stepTime);
        }
    }

    public void flee(Instance referenceInstance, Instance targetInstance, double stepTime){
        movementResponse(referenceInstance, targetInstance, "FLEE", stepTime);
    }

    public void follow(Instance referenceInstance, Instance targetInstance, double stepTime) {
        movementResponse(referenceInstance, targetInstance, "FOLLOW", stepTime);
    }

    public void patrol(Instance instance, List<Pair> patrolRoute, double stepTime) {
        int patrolStage = findPatrolStage(instance, patrolRoute);
        BasicComponent basic = instance.getComponent(BasicComponent.class);
        double[] distance = findDistanceVector(new Pair(basic.getX(), basic.getY()), patrolRoute.get(patrolStage));
        moveInDirection(instance, findDirection(distance), stepTime);
    }

    private int findPatrolStage(Instance instance, List<Pair> patrolRoute) {
        BasicComponent basic = instance.getComponent(BasicComponent.class);
        Pair<Double> topLeftCorner = new Pair(basic.getX(), basic.getY());
        Pair<Double> bottomRightCorner = new Pair(basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight());
        Pair<Double> earlierPoint;
        Pair<Double> nextPoint;

        for (int currentPatrolPathIndex = 0; currentPatrolPathIndex < patrolRoute.size(); currentPatrolPathIndex++) {
            earlierPoint = patrolRoute.get(currentPatrolPathIndex);
            nextPoint = patrolRoute.get(currentPatrolPathIndex + 1);
            Line line1 = new Line(earlierPoint.getItem1(), earlierPoint.getItem2(), nextPoint.getItem1(), nextPoint.getItem2());
            Line line2 = new Line(topLeftCorner.getItem1(), topLeftCorner.getItem2(), bottomRightCorner.getItem1(), bottomRightCorner.getItem2());

            if (line1.intersects(line2)) {
                return currentPatrolPathIndex + 1;
            }
        }
        return 0;
    }

    private boolean isInLOS(Instance targetInstance, Instance referenceInstance, double distance, double LOS) {
        return (LOS > distance && !targetEntityObscured(targetInstance, referenceInstance));
    }

    private double[] findDistanceVector(Instance referenceInstance, Instance targetInstance) {
        BasicComponent referenceBasic = referenceInstance.getComponent(BasicComponent.class);
        BasicComponent targetBasic = targetInstance.getComponent(BasicComponent.class);
        double deltaX = targetBasic.getX() - referenceBasic.getX();
        double deltaY = targetBasic.getY() - referenceBasic.getY();
        double[] vector = {deltaX, deltaY};
        return vector;
    }

    private double[] findDistanceVector(Pair<Double> referencePoint, Pair<Double> targetPoint) {
        double deltaX = targetPoint.getItem1() - referencePoint.getItem1();
        double deltaY = targetPoint.getItem2() - referencePoint.getItem2();
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

    private void moveInDirection(Instance instance, double[] direction, double stepTime){
        MotionComponent motion = instance.getComponent(MotionComponent.class);
        double tempVel = motion.getMovementVelocity();
        double tempXVel = tempVel * direction[0];
        double tempYVel = tempVel * direction[1];
        SetX setX = new setX(myGame);
        SetY setY = new setY(myGame);
        setX.activate(instance, tempXVel * stepTime);
        setY.activate(instance, tempYVel * stepTime);
    }


    private double calculateMagnitude(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        return Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), .5);
    }

    public void baseAim(Instance shooterInstance, Instance targetInstance, double accuracy){
        double[] distanceVec = findDistanceVector(shooterInstance, targetInstance);
        double angle = Math.atan(distanceVec[1] / distanceVec[0]);
        aim(shooterInstance, angle, accuracy);
    }

    public void goodAim(Instance shooterInstance, Instance targetInstance, double accuracy, double stepTime){
        MotionComponent motion = targetInstance.getComponent(MotionComponent.class);
        if (motion == null) baseAim(shooterInstance, targetInstance, accuracy);
        else {
            double xVel = motion.getXVelocity();
            double yVel = motion.getYVelocity();
            double[] distanceVec = findDistanceVector(shooterInstance, targetInstance);
            double idealX = distanceVec[0] + (stepTime * xVel);
            double idealY = distanceVec[1] + (stepTime * yVel);
            double[] idealDistanceVec = {idealX, idealY};
            double angle = Math.atan(idealDistanceVec[1] / idealDistanceVec[0]);
            aim(shooterInstance, angle, accuracy);
        }
    }

    private void aim(Instance shooterInstance, double angle, double accuracy){
        Random rand = new Random();
        angle += rand.nextGaussian() * angle * (1 - accuracy);
        AimComponent aim = shooterInstance.getComponent(AimComponent.class);
        aim.setXAim(Math.cos(angle));
        aim.setYAim(Math.sin(angle));
    }
}


