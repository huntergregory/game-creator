package Engine.src.Manager.Events.AI;

import Engine.src.ECS.Line;
import Engine.src.ECS.Pair;
import Engine.src.EngineData.Components.*;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Manager.Events.ComponentDependentEvent;
import Engine.src.Manager.Events.Motion.SetXPosition;
import Engine.src.Manager.Events.Motion.SetYPosition;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class AIEvent extends ComponentDependentEvent {

    private final double CORRECTION_ANGLE = 2;
    private final double CORRECTION_DISTANCE = 150;


    public AIEvent(Map<String, EngineInstance> engineInstanceSet, Class<?>... parameterTypes) {
        super(engineInstanceSet, BasicComponent.class, parameterTypes);
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
        LOSComponent LOSComp = referenceEngineInstance.getComponent(LOSComponent.class);
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

    public void follow(EngineInstance referenceEngineInstance, EngineInstance targetEngineInstance, double stepTime) {
        movementResponse(referenceEngineInstance, targetEngineInstance, "FOLLOW", stepTime);
    }

    public void patrol(EngineInstance engineInstance, List<Pair> patrolRoute, double stepTime) {
        int patrolStage = findPatrolStage(engineInstance, patrolRoute);
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
        double[] distance = findDistanceVector(new Pair(basic.getX(), basic.getY()), patrolRoute.get(patrolStage));
        moveInDirection(engineInstance, findDirection(distance), stepTime);
    }

    private int findPatrolStage(EngineInstance engineInstance, List<Pair> patrolRoute) {
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
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

    private void moveInDirection(EngineInstance engineInstance, double[] direction, double stepTime){
        MotionComponent motion = engineInstance.getComponent(MotionComponent.class);
        double tempVel = motion.getMovementVelocity();
        double tempXVel = tempVel * direction[0];
        double tempYVel = tempVel * direction[1];
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myEngineInstances, components);
        SetYPosition setY = new SetYPosition(myEngineInstances, components);
        setX.activate(engineInstance, tempXVel * stepTime);
        setY.activate(engineInstance, tempYVel * stepTime);
    }


    private double calculateMagnitude(double[] vector) {
        double deltaX = vector[0];
        double deltaY = vector[1];
        return Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), .5);
    }

    public void baseAim(EngineInstance shooterEngineInstance, EngineInstance targetEngineInstance, double accuracy){
        double[] distanceVec = findDistanceVector(shooterEngineInstance, targetEngineInstance);
        double angle = Math.atan(distanceVec[1] / distanceVec[0]);
        aim(shooterEngineInstance, angle, accuracy);
    }

    public void goodAim(EngineInstance shooterEngineInstance, EngineInstance targetEngineInstance, double accuracy, double stepTime){
        MotionComponent motion = targetEngineInstance.getComponent(MotionComponent.class);
        if (motion == null) baseAim(shooterEngineInstance, targetEngineInstance, accuracy);
        else {
            double xVel = motion.getXVelocity();
            double yVel = motion.getYVelocity();
            double[] distanceVec = findDistanceVector(shooterEngineInstance, targetEngineInstance);
            double idealX = distanceVec[0] + (stepTime * xVel);
            double idealY = distanceVec[1] + (stepTime * yVel);
            double[] idealDistanceVec = {idealX, idealY};
            double angle = Math.atan(idealDistanceVec[1] / idealDistanceVec[0]);
            aim(shooterEngineInstance, angle, accuracy);
        }
    }

    private void aim(EngineInstance shooterEngineInstance, double angle, double accuracy){
        Random rand = new Random();
        angle += rand.nextGaussian() * angle * (1 - accuracy);
        AimComponent aim = shooterEngineInstance.getComponent(AimComponent.class);
        aim.setXAim(Math.cos(angle));
        aim.setYAim(Math.sin(angle));
    }
}


