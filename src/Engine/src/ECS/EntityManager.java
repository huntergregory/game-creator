package Engine.src.ECS;

import Engine.src.Components.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.awt.geom.Line2D;
import java.util.Map;

public class EntityManager {
    private Map<Integer, Map<Class<? extends Component>, Component>> myEntityMap;
    private double myStepTime;

    public EntityManager(Map<Integer, Map<Class<? extends Component>, Component>> entityMap, double stepTime) {
        myEntityMap = entityMap;
        myStepTime = stepTime;
    }

    public void addComponent(int entityID, Component component) {
        try {
            var components = getAllComponents(entityID);
            components.put(component.getClass(), component);
            myEntityMap.put(entityID, components);
        }
        catch (NoEntityException e) {
            System.out.println("Entity " + entityID + " does not exist");
        }
    }

    //can return null
    public <T extends Component> T getComponent(int entityID, Class<T> componentClass){
        try {
            var components = getAllComponents(entityID);
            for (Class<?> componentName: components.keySet()) {
                if (componentName.equals(componentClass)) {
                    return (T) components.get(componentName);
                }
            }
            return null;
        }
        catch(NoEntityException e) {
            System.out.println("Entity " + entityID + " does not exist");
            return null;
        }
    }

    private boolean hasComponent(int entityID, Class<?> componentClass){
        return myEntityMap.containsKey(componentClass);
    }

    private Map<Class<? extends Component>, Component> getAllComponents(int entityID) throws NoEntityException {
        Map<Class<? extends Component>, Component> components = myEntityMap.get(entityID);
        if (components == null)
            throw new NoEntityException("Entity " + entityID + " does not exist");
        return components;
    }

    public void die(int entityID) {
        //TODO error checking, does removing a non-existent Entity work
        if(hasComponent(entityID, LivesComponent.class)){
            LivesComponent lives = getComponent(entityID, LivesComponent.class);
            if (lives.expired()) myEntityMap.remove(entityID);
            else {
                respawn(entityID, lives.getRespawnInstructions());
                lives.removeLife();
            }
        }
        else myEntityMap.remove(entityID);
    }

    private void respawn(int entityID, String respawnInstructions){
        Binding binding = new Binding();
        binding.setProperty("ID", entityID);
        binding.setProperty("entityManager", this);
        GroovyShell shell = new GroovyShell(binding);
        Script instructions = shell.parse(respawnInstructions);
        instructions.run();
    }

    public void setToCheckpoint(int entityID){
        CheckpointComponent checkpoint = getComponent(entityID, CheckpointComponent.class);
        BasicComponent basic = getComponent(entityID, BasicComponent.class);
        basic.setX(checkpoint.getX());
        basic.setY(checkpoint.getY());
    }

    public void create(int entityID, Map<Class<? extends Component>, Component> components) {
        //TODO error checking
        myEntityMap.put(entityID, components);
    }

    public void move(int entityID) {
        var motionComponent = getComponent(entityID, MotionComponent.class);
        var basicComponent = getComponent(entityID, BasicComponent.class);
        if (motionComponent != null && basicComponent != null) {
            double newX = motionComponent.getNewX(basicComponent.getX());
            double newY = motionComponent.getNewY(basicComponent.getY());
            basicComponent.setX(newX);
            basicComponent.setY(newY);
        }
    }

    public void adjustDirection(int entityID, double delta) {
        var motionComponent = getComponent(entityID, MotionComponent.class);
        if (motionComponent != null)
            motionComponent.adjustDirection(delta);
    }

    public void setDirection(int entityID, double angle){
        var motionComponent = getComponent(entityID, MotionComponent.class);
        if (motionComponent != null)
            motionComponent.setDirection(angle);
    }

    public void setYVelocity(int entityID, double vel) {
        var motionComponent = getComponent(entityID, MotionComponent.class);
        if (motionComponent != null)
            motionComponent.setYVelocity(vel);
    }

    public void setXVelocity(int entityID, double vel) {
        var motionComponent = getComponent(entityID, MotionComponent.class);
        if (motionComponent != null)
            motionComponent.setXVelocity(vel);
    }

    public void adjustHealth(int entityID, int delta) {
        var healthComponent = getComponent(entityID, HealthComponent.class);
        if (healthComponent != null) {
            int currentHealth = healthComponent.getHealth();
            int maxHealth = healthComponent.getMaxHealth();
            if (healthComponent.getMaxHealth() == 0) {
                healthComponent.setHealth(currentHealth + delta);
            }
            else {
                int newHealth = Math.min(currentHealth + delta, maxHealth);
                healthComponent.setHealth(newHealth);
            }
        }
    }

    public void setHealth(int entityID, int health) {
        var healthComponent = getComponent(entityID, HealthComponent.class);
        if (healthComponent != null) {
            int maxHealth = healthComponent.getMaxHealth();
            if (health < maxHealth) {
                healthComponent.setHealth(health);
            }
            //is it necessary to throw an error or just set it to the max health?
            else {
                healthComponent.setHealth(maxHealth);
            }
        }
    }

    //Not sure if done here and how states would be referenced non-specifically

    public void increaseScore(int entityID, double gain) {
        ScoreComponent score = getComponent(entityID, ScoreComponent.class);
        score.setScore(score.getScore() + gain);
    }

    public boolean hasState(int entityID, String state){
        StateComponent states = getComponent(entityID, StateComponent.class);
        return states.hasState(state);
    }

    public void addState(int entityID, String state){
        StateComponent states = getComponent(entityID, StateComponent.class);
        states.addState(state);
    }

    public void removeState(int entityID, String state){
        StateComponent states = getComponent(entityID, StateComponent.class);
        states.removeState(state);
    }

    public boolean healthBelow(int entityID, double threshhold){
        HealthComponent health = getComponent(entityID, HealthComponent.class);
        return health.getHealth() < threshhold;
    }

    public void addPowerup(int entityID, int otherEntityID) {

    }

    public void stop(int entityID) {
        var motionComponent = (MotionComponent) getComponent(entityID, MotionComponent.class);
        if (motionComponent != null) {
            //might want to split this up into x and y stop methods
            motionComponent.setXVelocity(0);
            motionComponent.setYVelocity(0);
        }
    }

    public void jump(int entityID) {
        var jumpComponent = (JumpComponent) getComponent(entityID, JumpComponent.class);
        var motionComponent = (MotionComponent) getComponent(entityID, MotionComponent.class);
        if (jumpComponent != null && motionComponent != null) {
            double jumpVelocity = jumpComponent.getJumpVelocity();
            motionComponent.setYVelocity(jumpVelocity);
        }
    }

    //TODO remove duplication between horizontal and vertical
    public void moveVertical(Integer entity, boolean down) {
        var basic = getComponent(entity, BasicComponent.class);
        var motion = getComponent(entity, MotionComponent.class);
        if (basic == null || motion == null)
            return;

        double yPos = basic.getY();
        double yVel = motion.getMovementYVelocity();
        int direction = down ? 1 : -1;
        setY(entity, yPos + direction * yVel * myStepTime);
    }

    //TODO remove duplication between horizontal and vertical
    public void moveHorizontal(Integer entity, boolean right) {
        var basic = getComponent(entity, BasicComponent.class);
        var motion = getComponent(entity, MotionComponent.class);
        if (basic == null || motion == null)
            return;

        double xPos = basic.getX();
        double xVel = motion.getMovementXVelocity();
        int direction = right ? 1 : -1;
        setX(entity, xPos + direction * xVel * myStepTime);
    }

    //TODO remove duplication between setY and also in collision handler and detector
    public void setX(int obj, double newX){
        BasicComponent basic = getComponent(obj, BasicComponent.class);
        double currentX = basic.getX();
        double finalX = newX;
        CollisionDetector collisionDetector = new CollisionDetector(this);
        Integer[] impassableColliders = collisionDetector.getImpassableColliders(obj, myEntityMap.keySet());
        for(Integer impassable : impassableColliders){
            if ((collisionDetector.collideFromLeft(impassable, obj) && newX > currentX) ||
                (collisionDetector.collideFromLeft(obj, impassable) && newX < currentX)) {
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
            if ((collisionDetector.collideFromTop(impassable, obj) && newY > currentY) ||
                    (collisionDetector.collideFromTop(impassable, obj) && newY < currentY)) {
                finalY = currentY;
            }
        }
        basic.setY(finalY);
    }

    public void rotateAimClockwise(int obj){
        rotateAim(obj, "CLOCKWISE");
    }

    public void rotateAimCClockwise(int obj){
        rotateAim(obj, "CCLOCKWISE");
    }

    private void rotateAim(int obj, String direction){
        AimComponent aim = getComponent(obj, AimComponent.class);
        double currentAngle = Math.atan(aim.getYAim()/aim.getXAim());
        double newAngle;
        if(direction.equals("CLOCKWISE")) newAngle = currentAngle + aim.getRotationRate();
        else newAngle = currentAngle - aim.getRotationRate();
        aim.setXAim(Math.cos(newAngle));
        aim.setYAim(Math.sin(newAngle));
    }

    public double getStepTime() {
        return myStepTime;
    }

    public void moveInDirection(int entityID, double[] direction){
        MotionComponent motion = getComponent(entityID, MotionComponent.class);
        double tempVel = motion.getMovementVelocity();
        double tempXVel = tempVel * direction[0];
        double tempYVel = tempVel * direction[1];
        setX(entityID, tempXVel * getStepTime());
        setY(entityID, tempYVel * getStepTime());
    }

    public boolean targetEntityObscured(int targetID, int referenceID) {
        BasicComponent targetBasic = getComponent(targetID, BasicComponent.class);
        double targetX = targetBasic.getX();
        double targetY = targetBasic.getY();
        return obscured(targetX, targetY, targetID, referenceID);
    }

    public boolean targetPointObscured(double targetLocationX, double targetLocationY, int referenceID){
        return obscured(targetLocationX, targetLocationY, -1, referenceID);
    }

    private boolean obscured(double targetLocationX, double targetLocationY, double targetID, int referenceID) {
        BasicComponent referenceBasic = getComponent(referenceID, BasicComponent.class);
        double referenceX = referenceBasic.getX();
        double referenceY = referenceBasic.getY();

        for (int ID : myEntityMap.keySet()){
            if (ID != targetID && ID != referenceID){
                BasicComponent basic = getComponent(ID, BasicComponent.class);
                double[] topLeftCorner = {basic.getX(), basic.getY()};
                double[] bottomRightCorner = {basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight()};
                if (Line2D.linesIntersect(targetLocationX, targetLocationY, referenceX, referenceY,
                        topLeftCorner[0], topLeftCorner[1], bottomRightCorner[0], bottomRightCorner[1])){
                    return true;
                }
            }
        }

        return false;
    }

    public void addLogic(int entityID, String additionalLogic) {
        LogicComponent logic = getComponent(entityID, LogicComponent.class);
        logic.setLogic(logic.getLogic() + additionalLogic);
    }

}
