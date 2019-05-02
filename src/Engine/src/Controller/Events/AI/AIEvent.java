package Engine.src.Controller.Events.AI;

import Engine.src.ECS.Line;
import Engine.src.ECS.Pair;
import Engine.src.EngineData.ComponentExceptions.NoComponentException;
import Engine.src.EngineData.Components.*;
import Engine.src.EngineData.EngineGameObject;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.Controller.Events.Motion.SetXPosition;
import Engine.src.Controller.Events.Motion.SetYPosition;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class AIEvent extends ComponentDependentEvent {

    private final double CORRECTION_ANGLE = 2;
    private final double CORRECTION_DISTANCE = 150;


    public AIEvent(Map<String, EngineInstance> engineInstanceSet, Set<UnmodifiableEngineGameObject> engineObjects, Class<?>... parameterTypes) {
        super(engineInstanceSet, engineObjects, BasicComponent.class, parameterTypes);
    }

    private boolean targetEntityObscured(EngineInstance targetEngineInstance, EngineInstance referenceEngineInstance) {
        BasicComponent targetBasic = targetEngineInstance.getComponent(BasicComponent.class);
        double targetX = targetBasic.getX();
        double targetY = targetBasic.getY();
        return obscured(targetX, targetY, targetEngineInstance.getID(), referenceEngineInstance);
    }

    private boolean targetPointObscured(double targetLocationX, double targetLocationY, EngineInstance referenceEngineInstance){
        return obscured(targetLocationX, targetLocationY, "EMPTY", referenceEngineInstance);
    }

    private boolean obscured(double targetLocationX, double targetLocationY, String targetID, EngineInstance referenceEngineInstance) {
        BasicComponent referenceBasic = referenceEngineInstance.getComponent(BasicComponent.class);
        double referenceX = referenceBasic.getX();
        double referenceY = referenceBasic.getY();

        for (String ID : myEngineInstances.keySet()){
            EngineInstance engineInstance = myEngineInstances.get(ID);
            if (!ID.equals(targetID) && !ID.equals(referenceEngineInstance.getID())){
                BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
                Pair<Double> topLeftCorner = new Pair(basic.getX(), basic.getY());
                Pair<Double> bottomRightCorner = new Pair(basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight());

                Line line1 = new Line(targetLocationX, targetLocationY, referenceX, referenceY);
                Line line2 = new Line(topLeftCorner.getItem1(), topLeftCorner.getItem2(), bottomRightCorner.getItem1(), bottomRightCorner.getItem2());

                return line1.intersects(line2);
            }
        }

        return false;
    }

    private void movementResponse(EngineInstance referenceEngineInstance, EngineInstance targetEngineInstance, String movementType, double stepTime) {
        LOSComponent LOSComp;
        try {
            LOSComp = referenceEngineInstance.getComponent(LOSComponent.class);
        }
        catch (NoComponentException e) {
            LOSComp = null;
        }
        double[] distanceVec = findDistanceVector(referenceEngineInstance, targetEngineInstance);
        double magnitude = calculateMagnitude(distanceVec);
        if (LOSComp == null || isInLOS(targetEngineInstance, referenceEngineInstance, magnitude, LOSComp.getLOS())) {
            double[] direction = findDirection(distanceVec);
            if(movementType.equals("FLEE")) {
                direction[0] = direction[0] * -1;
                direction[1] = direction[1] * -1;
                BasicComponent basic = referenceEngineInstance.getComponent(BasicComponent.class);
                double currentX = basic.getX();
                double currentY = basic.getY();
                double[] targetLocation = {currentX + (CORRECTION_DISTANCE * direction[0]), currentY + (CORRECTION_DISTANCE * direction[1])};
                while(targetPointObscured(targetLocation[0], targetLocation[1], referenceEngineInstance)){
                    double angle = Math.atan(currentY / currentX);
                    angle += CORRECTION_ANGLE;
                    direction[0] = Math.cos(angle);
                    direction[1] = Math.sin(angle);
                    double[] newDirection = {currentX + (CORRECTION_DISTANCE * direction[0]), currentY + (CORRECTION_DISTANCE * direction[1])};
                    targetLocation = newDirection;
                }
            }
            moveInDirection(referenceEngineInstance, direction, stepTime);
        }
    }

    public void flee(EngineInstance referenceEngineInstance, EngineInstance targetEngineInstance, double stepTime){
        movementResponse(referenceEngineInstance, targetEngineInstance, "FLEE", stepTime);
    }

    public void follow(EngineInstance referenceEngineInstance, String target, double stepTime) {
        EngineInstance targetEngineInstance = getInstanceByID(target);
        movementResponse(referenceEngineInstance, targetEngineInstance, "FOLLOW", stepTime);
    }

    public void patrol(EngineInstance engineInstance, List<List<Double>> patrolRoute, double stepTime) {
        int patrolStage = findPatrolStage(engineInstance, patrolRoute);
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
        System.out.println(basic.getX() + "  " + basic.getY());
        double[] distance = findDistanceVector(new double[]{basic.getX(), basic.getY()}, patrolRoute.get(patrolStage));
        moveInDirection(engineInstance, findDirection(distance), stepTime);
    }

    private int findPatrolStage(EngineInstance engineInstance, List<List<Double>> patrolRoute) {
        return new Random().nextInt(patrolRoute.size());
        /*
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
        double[] topLeftCorner = {basic.getX(), basic.getY()};
        double[] bottomRightCorner = {basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight()};
        Double[] earlierPoint;
        Double[] nextPoint;

        for (int currentPatrolPathIndex = 0; currentPatrolPathIndex < patrolRoute.size() - 1; currentPatrolPathIndex++) {
            earlierPoint = new Double[]{patrolRoute.get(currentPatrolPathIndex).get(0), patrolRoute.get(currentPatrolPathIndex).get(1)};
            nextPoint = new Double[]{patrolRoute.get(currentPatrolPathIndex + 1).get(0), patrolRoute.get(currentPatrolPathIndex + 1).get(1)};
            Line line1 = new Line(earlierPoint[0], earlierPoint[1], nextPoint[0], nextPoint[1]);
            Line line2 = new Line(topLeftCorner[0], topLeftCorner[1], bottomRightCorner[0], bottomRightCorner[1]);

            if (line1.intersects(line2)) {
                return currentPatrolPathIndex + 1;
            }
        }
        return 0;
        */
    }

    private boolean isInLOS(EngineInstance targetEngineInstance, EngineInstance referenceEngineInstance, double distance, double LOS) {
        return (LOS > distance && !targetEntityObscured(targetEngineInstance, referenceEngineInstance));
    }

    private double[] findDistanceVector(EngineInstance referenceEngineInstance, EngineInstance targetEngineInstance) {
        BasicComponent referenceBasic = referenceEngineInstance.getComponent(BasicComponent.class);
        BasicComponent targetBasic = targetEngineInstance.getComponent(BasicComponent.class);
        double deltaX = targetBasic.getX() - referenceBasic.getX();
        double deltaY = targetBasic.getY() - referenceBasic.getY();
        double[] vector = {deltaX, deltaY};
        return vector;
    }

    private double[] findDistanceVector(double[] referencePoint, List<Double> targetPoint) {
        double deltaX = targetPoint.get(0) - referencePoint[0];
        double deltaY = targetPoint.get(1) - referencePoint[1];
        return new double[]{deltaX, deltaY};
    }

    private double[] findDirection(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        double magnitude = calculateMagnitude(vector);
        return new double[]{deltaX / magnitude, deltaY / magnitude};
    }

    private void moveInDirection(EngineInstance engineInstance, double[] direction, double stepTime){
        MotionComponent motion = engineInstance.getComponent(MotionComponent.class);
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
        double currXPos = basic.getX();
        double currYPos = basic.getY();
        double tempVel = motion.getMovementVelocity();
        double tempXVel = tempVel * direction[0];
        double tempYVel = tempVel * direction[1];
        double nextXPos = currXPos + tempXVel * stepTime;
        double nextYPos = currYPos + tempYVel * stepTime;
        SetXPosition setX = new SetXPosition(myEngineInstances, myGameEngineObjects);
        SetYPosition setY = new SetYPosition(myEngineInstances, myGameEngineObjects);
        setX.activate(engineInstance, stepTime, nextXPos);
        setY.activate(engineInstance, stepTime, nextYPos);
    }


    private double calculateMagnitude(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        return Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), .5);
    }

    public void baseAim(EngineInstance shooterEngineInstance, EngineInstance targetEngineInstance, String missile, double accuracy){
        double[] distanceVec = findDistanceVector(shooterEngineInstance, targetEngineInstance);
        double angle = Math.atan(distanceVec[1] / distanceVec[0]);
        aimAndShoot(shooterEngineInstance, missile, angle, accuracy);
    }

    public void goodAim(EngineInstance shooterEngineInstance, EngineInstance targetEngineInstance, String missile, double accuracy, double stepTime){
        MotionComponent motion = targetEngineInstance.getComponent(MotionComponent.class);
        if (motion == null) baseAim(shooterEngineInstance, targetEngineInstance, missile, accuracy);
        else {
            double xVel = motion.getXVelocity();
            double yVel = motion.getYVelocity();
            double[] distanceVec = findDistanceVector(shooterEngineInstance, targetEngineInstance);
            double idealX = distanceVec[0] + (stepTime * xVel);
            double idealY = distanceVec[1] + (stepTime * yVel);
            double[] idealDistanceVec = {idealX, idealY};
            double angle = Math.atan(idealDistanceVec[1] / idealDistanceVec[0]);
            aimAndShoot(shooterEngineInstance, missile, angle, accuracy);
        }
    }

    private void aimAndShoot(EngineInstance shooterEngineInstance, String missileObject, double angle, double accuracy){
        Random rand = new Random();
        angle += rand.nextGaussian() * angle * (1 - accuracy);
        AimComponent aim = shooterEngineInstance.getComponent(AimComponent.class);
        aim.setXAim(Math.cos(angle));
        aim.setYAim(Math.sin(angle));
        aim.updateTracker();
        if (aim.getMyTracker() % aim.getMyShootRate() == 0){
            shoot(shooterEngineInstance, missileObject, aim);
        }
    }

    private void shoot(EngineInstance shooterEngineInstance, String missile, AimComponent aim){
        EngineInstance missileInstance = createFromInstance(missile, shooterEngineInstance);
        MotionComponent motion = missileInstance.getComponent(MotionComponent.class);
        double velocity = motion.getVelocity();
        motion.setXVelocity(aim.getXAim() * velocity);
        motion.setYVelocity(aim.getYAim() * velocity);
    }

}


 