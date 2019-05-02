package Engine.src.ECS;

import Engine.src.EngineData.ComponentExceptions.NoComponentException;
import Engine.src.EngineData.Components.*;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Manager;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;

import static java.lang.Math.abs;

public class CollisionHandler {
    private static final String COLLISION_KEYWORD1 = "object1";
    private static final String COLLISION_KEYWORD2 = "object2";

    private Manager myManager;
    private CollisionDetector myCollisionDetector;
    private Map<Pair<String>, String> myCollisionResponses;
    private Map<EngineInstance, Map<String, EngineInstance>> myPreviousCollisions;
    private Map<EngineInstance, Map<String, EngineInstance>> myCurrentCollisions;
    private Binding mySetter;

    public CollisionHandler(Manager manager) {
        myManager = manager;
        myCollisionResponses = new HashMap<>();
        myPreviousCollisions = new HashMap<>();
        myCurrentCollisions = new HashMap<>();
        myCollisionDetector = new CollisionDetector();
        mySetter = new Binding();
        mySetter.setProperty("manager", myManager);
        mySetter.setProperty("collisionDetector", myCollisionDetector);
    }

    //assumes collisionResponses and entities are nonnull
    public void handleCollisions(Map<String, EngineInstance> allEntities, Map<Pair<String>, String> collisionResponses) {
        myCollisionResponses = collisionResponses;
        myCurrentCollisions = new HashMap<>();
        moveThenUpdateVelocities(allEntities);

        List<EngineInstance> allEntitiesList = new ArrayList<>();
        for(String ID : allEntities.keySet()) {
            allEntitiesList.add(allEntities.get(ID));
        }

        for (int k = 0; k < allEntitiesList.size(); k++) {
            for (int j = 0; j < allEntitiesList.size(); j++) {
                if (k >= j) {
                    continue;
                }
                checkCollision(allEntitiesList.get(k), allEntitiesList.get(j));
            }
        }

        for (String ID : allEntities.keySet()) {
            EngineInstance entity = allEntities.get(ID);
            if (!isInteractingWithEnvironment(entity))
                setDefaultMotion(entity);
        }
        myPreviousCollisions = myCurrentCollisions;
    }

    private void moveThenUpdateVelocities(Map<String, EngineInstance> allEntities) {
        for (String ID : allEntities.keySet()){
            EngineInstance i = allEntities.get(ID);
            try {
                var motionComponent = i.getComponent(MotionComponent.class);
                myManager.call("Move", i);
                myManager.call("UpdateVelocity", i);
            }
            catch(NoComponentException e) {
            }
        }
    }

    private boolean isInteractingWithEnvironment(EngineInstance i) {
        if (myCurrentCollisions.containsKey(i)) {
            Map<String, EngineInstance> possibleEnvironments = myCurrentCollisions.get(i);
            for (String ID : possibleEnvironments.keySet()) {
                EngineInstance possibleEnvironment = possibleEnvironments.get(ID);
                try {
                    var envComponent = possibleEnvironment.getComponent(EnvironmentComponent.class);
                    return true;
                }
                catch(NoComponentException e) {
                    continue;
                }
            }
        }
        return false;
    }

    private void setDefaultMotion(EngineInstance i) {
        try {
            var motion = i.getComponent(MotionComponent.class);
            motion.resetXAccel();
            motion.resetYAccel();
            motion.resetMovementXVel();
            motion.resetMovementYVel();
        }
        catch(NoComponentException e) {
            System.out.println("No motion component");
        }
    }

    private void checkCollision(EngineInstance i, EngineInstance j) {
        Pair<String> collisionTagPair = findRelevantTagPairs(i, j);
        if (!myCollisionDetector.collides(i, j))
            return;
        if (collisionTagPair != null ) {
            addCollisionAndHandleEnvironments(i, j);
            addCollisionAndHandleEnvironments(j, i);
        }
        correctClipping(i, j);
        correctClipping(j, i);
        dealWithImpassable(i, j);
        dealWithImpassable(j, i);
        if (collisionTagPair != null ) {
            String responses = myCollisionResponses.get(collisionTagPair);
            activateEvents(i, j, responses);
        }
    }

    private void correctClipping(EngineInstance toAdjust, EngineInstance other) {
        try {
            var environmentComponent = other.getComponent(EnvironmentComponent.class);
        }
        catch (NoComponentException e) {
            try {
                var impassableComponent = other.getComponent(ImpassableComponent.class);
                var motionComponent = toAdjust.getComponent(MotionComponent.class);
                var adjBasic = toAdjust.getComponent(BasicComponent.class);
                var otherBasic = other.getComponent(BasicComponent.class);
                Map<String, Double> shortestClipping = new HashMap<>();
                if (myCollisionDetector.collideFromTop(toAdjust, other)) {
                    shortestClipping.put("top", otherBasic.getY() - adjBasic.getHeight() - adjBasic.getY());
                }
                if (myCollisionDetector.collideFromTop(other, toAdjust)) {
                    shortestClipping.put("bottom", otherBasic.getY() + otherBasic.getHeight() - adjBasic.getY());
                }
                if (myCollisionDetector.collideFromLeft(toAdjust, other)) {
                    shortestClipping.put("left", otherBasic.getX() - adjBasic.getWidth() - adjBasic.getX());
                }
                if (myCollisionDetector.collideFromLeft(other, toAdjust)) {
                    shortestClipping.put("right", otherBasic.getX() + otherBasic.getWidth() - adjBasic.getX());
                }
                Map.Entry<String, Double> min = null;
                for (Map.Entry<String, Double> entry : shortestClipping.entrySet()) {
                    if (min == null || abs(min.getValue()) > abs(entry.getValue())) {
                        min = entry;
                    }
                }
                if (min != null) {
                    if (min.getKey().equals("top") || min.getKey().equals("bottom")) {
                        adjBasic.setY(adjBasic.getY() + min.getValue());
                    }
                    else if (min.getKey().equals("left") || min.getKey().equals("right")) {
                        adjBasic.setX(adjBasic.getX() + min.getValue());
                    }
                }
            }
            catch(NoComponentException e2) {
                //System.out.println("Correct Clipping cannot be done");
            }
        }
    }

    private void dealWithImpassable(EngineInstance mover, EngineInstance impassable) {
        try {
            var impassableComponent = impassable.getComponent(ImpassableComponent.class);
            if (impassableComponent != null && impassableComponent.getImpassable()) {
                var motion = mover.getComponent(MotionComponent.class);
                if (motion == null)
                    return;
                if (myCollisionDetector.collideFromTop(mover, impassable) && motion.getYVelocity() > 0
                        || myCollisionDetector.collideFromTop(impassable, mover) && motion.getYVelocity() < 0) {
                    motion.setYVelocity(0);
                }
                else if (myCollisionDetector.collideFromLeft(mover, impassable) && motion.getXVelocity() > 0
                        || myCollisionDetector.collideFromLeft(impassable, mover) && motion.getXVelocity() < 0) {
                    motion.setXVelocity(0);
                }
            }
        }
        catch (NoComponentException e) {
            System.out.println("No impassable to deal with");
        }
    }

    private Pair<String> findRelevantTagPairs(EngineInstance i, EngineInstance j) {
        ArrayList<Pair<String>> tagPairs = new ArrayList<>();
        String objectType1 = i.getType();
        String objectType2 = j.getType();
        var tagPair = new Pair(objectType1, objectType2);

        if(myCollisionResponses.containsKey(tagPair)) {
            return tagPair;
        }
        return null;
    }

    private void addCollisionAndHandleEnvironments(EngineInstance current, EngineInstance other) {
        myCurrentCollisions.putIfAbsent(current, new HashMap<>());
        myCurrentCollisions.get(current).put(other.getID(), other);
        try {
            var currentMotionComponent = current.getComponent(MotionComponent.class);
            var otherEnvironmentComponent = other.getComponent(EnvironmentComponent.class);
            if (myPreviousCollisions.containsKey(current) && !myPreviousCollisions.get(current).containsKey(other.getID()))
                setInEnvironment(current, currentMotionComponent, otherEnvironmentComponent);
        }
        catch (NoComponentException e) {
            System.out.println("No environment component OR no motion component");
        }
    }

    private void setInEnvironment(EngineInstance i, MotionComponent motion, EnvironmentComponent environment) {
        motion.setXAccel(environment.getUpdatedAccel(motion.getXVelocity()));
        motion.setYAccel(environment.getUpdatedAccel(motion.getYVelocity()));
        if (motion.getMovementXVelocity() != motion.getDefaultMovementXVel()) {
            motion.setMovementXVelocity(environment.getUpdatedMovementVelocity(motion.getMovementXVelocity(), motion.getDefaultMovementXVel()));
        }
        if (motion.getMovementYVelocity() != motion.getDefaultMovementYVel()) {
            motion.setMovementXVelocity(environment.getUpdatedMovementVelocity(motion.getMovementYVelocity(), motion.getDefaultMovementYVel()));
        }
    }

    //TODO fix if Timers.Events are changed
    private void activateEvents(EngineInstance engineInstance1, EngineInstance engineInstance2, String responses) {
        //FIXME delegate rest of method to ObjectEvent/GameEvent and uncomment code above

        GroovyShell shell = new GroovyShell(mySetter);
        mySetter.setProperty(COLLISION_KEYWORD1, engineInstance1);
        mySetter.setProperty(COLLISION_KEYWORD2, engineInstance2);
        System.out.println(mySetter.getProperty("manager"));
        System.out.println(responses);
        Script script = shell.parse(responses);
        script.run();

    }

    public void addCollision(String type1, String type2, String responses){
        myCollisionResponses.put(new Pair<>(type1, type2), responses);
    }
}
