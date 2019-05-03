package Engine.src.ECS;

<<<<<<< HEAD
import Engine.src.EngineData.Components.*;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Manager;

=======
import Engine.src.Components.EnvironmentComponent;
import Engine.src.Components.ImpassableComponent;
import Engine.src.Components.MotionComponent;
import Engine.src.Components.TagsComponent;
import Engine.src.Controller.LevelManager;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;


/**
 * FIXME
 * Bugs: default gravity etc not set
 *       impassables don't allow movement at all
 */
public class CollisionHandler {
<<<<<<< HEAD
    private static final String COLLISION_KEYWORD1 = "object1";
    private static final String COLLISION_KEYWORD2 = "object2";

    private Manager myManager;
=======
    private EntityManager myEntityManager;
    private LevelManager myLevelManager;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
    private CollisionDetector myCollisionDetector;
    private Map<Pair<String>, Pair<String>> myCollisionResponses;
    private Map<Integer, Set<Integer>> myPreviousCollisions;
    private Map<Integer, Set<Integer>> myCurrentCollisions;
    private Map<Integer, EnvironmentComponent> myEntityCurrentEnvironments;
    private Binding mySetter;

    //FIXME
    private static final double MY_DEFAULT_ACCEL_Y = 5;
    private static final double MY_DEFAULT_ACCEL_X = 0;
    //FIXME

    public CollisionHandler(EntityManager objectManager, LevelManager levelManager) {
        myEntityManager = objectManager;
        myLevelManager = levelManager;
        myCollisionResponses = new HashMap<>();
        myPreviousCollisions = new HashMap<>();
        myCurrentCollisions = new HashMap<>();
        myEntityCurrentEnvironments = new HashMap<>();
        myCollisionDetector = new CollisionDetector(myEntityManager);
        mySetter = new Binding();
        mySetter.setProperty("entityManager", myEntityManager);
        mySetter.setProperty("levelManager", myLevelManager);
        mySetter.setProperty("collisionDetector", myCollisionDetector);
    }

    //assumes collisionResponses and entities are nonnull
    public void handleCollisions(Set<Integer> entities, Map<Pair<String>, Pair<String>> collisionResponses) {
        myCollisionResponses = collisionResponses;
        myCurrentCollisions = new HashMap<>();
<<<<<<< HEAD
        moveThenUpdateVelocities(allEntities);

        List<EngineInstance> allEntitiesList = new ArrayList<>();
        for(String ID : allEntities.keySet()) {
            allEntitiesList.add(allEntities.get(ID));
        }
=======

        moveThenUpdateVelocities(entities);
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc

        for (Integer entity1 : entities) {
            for (Integer entity2 : entities) {
                if (entity1 >= entity2) //prevent collisions from happening twice
                    continue;
                checkCollision(entity1, entity2);
            }
        }

        for (Integer entity : entities) {
            if (notInteractingWithEnvironment(entity))
                setInDefaultEnvironment(entity);
        }
        myPreviousCollisions = myCurrentCollisions;
    }

<<<<<<< HEAD
    private void moveThenUpdateVelocities(Map<String, EngineInstance> allEntities) {
        for (String ID : allEntities.keySet()){
            EngineInstance i = allEntities.get(ID);
            if(i.hasComponent(MotionComponent.class)) {
                var motionComponent = i.getComponent(MotionComponent.class);
                myManager.call("Move", i);
                myManager.call("UpdateVelocity", i);
=======
    private void moveThenUpdateVelocities(Set<Integer> entities) {
        for (int id : entities){
            var motionComponent = myEntityManager.getComponent(id, MotionComponent.class);
            if (motionComponent != null) {
                myEntityManager.move(id);
                motionComponent.updateVelocity();
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
            }
        }
    }

<<<<<<< HEAD
    private boolean isInteractingWithEnvironment(EngineInstance i) {
        if (myCurrentCollisions.containsKey(i)) {
            Map<String, EngineInstance> possibleEnvironments = myCurrentCollisions.get(i);
            for (String ID : possibleEnvironments.keySet()) {
                EngineInstance possibleEnvironment = possibleEnvironments.get(ID);
                if (possibleEnvironment.hasComponent(EnvironmentComponent.class)) {
=======
    private boolean notInteractingWithEnvironment(Integer entity) {
        if (myCurrentCollisions.containsKey(entity)) {
            Set<Integer> possibleEnvironments = myCurrentCollisions.get(entity);
            for (Integer possibleEnvironment : possibleEnvironments) {
                if (myEntityManager.getComponent(possibleEnvironment, EnvironmentComponent.class) != null)
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
                    return true;
                }
            }
        }
        return false;
    }

<<<<<<< HEAD
    private void setDefaultMotion(EngineInstance i) {
        if(i.hasComponent(MotionComponent.class)) {
            var motion = i.getComponent(MotionComponent.class);
            motion.resetXAccel();
            motion.resetYAccel();
            motion.resetMovementXVel();
            motion.resetMovementYVel();
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
        if (!other.hasComponent(EnvironmentComponent.class)) {
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
    }

    private void dealWithImpassable(EngineInstance mover, EngineInstance impassable) {
        if(impassable.hasComponent(ImpassableComponent.class)) {
            var impassableComponent = impassable.getComponent(ImpassableComponent.class);
            if (impassableComponent.getImpassable()) {
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
    }

    private Pair<String> findRelevantTagPairs(EngineInstance i, EngineInstance j) {
=======
    private void setInDefaultEnvironment(Integer entity) {
        myEntityCurrentEnvironments.put(entity, null);
        var motion = myEntityManager.getComponent(entity, MotionComponent.class);
        if (motion != null) {
            motion.setXAcceleration(MY_DEFAULT_ACCEL_X);
            motion.setYAcceleration(MY_DEFAULT_ACCEL_Y);
        }
    }

    private void checkCollision(Integer entity1, Integer entity2) {
        Pair<String>[] collisionTagPairs = findRelevantTagPairs(entity1, entity2);
        if (!myCollisionDetector.collides(entity1, entity2))
            return;
        if (collisionTagPairs.length != 0 ) {

            handleEnvironments(entity1, entity2);
            handleEnvironments(entity2, entity1);

            for (Pair<String> tagPair : collisionTagPairs) {
                Pair<String> responseListPair = myCollisionResponses.get(tagPair);
                activateEvents(entity1, entity2, responseListPair.getItem1());
                activateEvents(entity2, entity1, responseListPair.getItem2());
            }
        }

        dealWithImpassable(entity1, entity2);
        dealWithImpassable(entity2, entity1);
    }

    //FIXME duplicated between parts of collision handler and parts of Entity manager
    private void dealWithImpassable(Integer mover, Integer impassable) {
        var impassableComponent = myEntityManager.getComponent(impassable, ImpassableComponent.class);
        if (impassableComponent != null && impassableComponent.getImpassable()) {
            var motion = myEntityManager.getComponent(mover, MotionComponent.class);
            if (motion == null)
                return;
            motion.setYVelocity(0);
            /*if (myCollisionDetector.collideFromTop(mover, impassable) && motion.getYVelocity() > 0
                    || myCollisionDetector.collideFromTop(impassable, mover) && motion.getYVelocity() < 0) {
                motion.setYVelocity(0);
            }
            else if (myCollisionDetector.collideFromLeft(mover, impassable) && motion.getXVelocity() > 0
                    || myCollisionDetector.collideFromLeft(impassable, mover) && motion.getXVelocity() < 0) {
                motion.setXVelocity(0);
            }*/
        }
    }

    private Pair<String>[] findRelevantTagPairs(Integer entity1, Integer entity2) {
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
        ArrayList<Pair<String>> tagPairs = new ArrayList<>();
        String objectType1 = i.getType();
        String objectType2 = j.getType();
        var tagPair = new Pair(objectType1, objectType2);

<<<<<<< HEAD
        if(myCollisionResponses.containsKey(tagPair)) {
            return tagPair;
        }
        return null;
    }

    private void addCollisionAndHandleEnvironments(EngineInstance current, EngineInstance other) {
        myCurrentCollisions.putIfAbsent(current, new HashMap<>());
        myCurrentCollisions.get(current).put(other.getID(), other);
        if (current.hasComponent(MotionComponent.class) && other.hasComponent(EnvironmentComponent.class)) {
            var currentMotionComponent = current.getComponent(MotionComponent.class);
            var otherEnvironmentComponent = other.getComponent(EnvironmentComponent.class);
            if (myPreviousCollisions.containsKey(current) && !myPreviousCollisions.get(current).containsKey(other.getID()))
                setInEnvironment(current, currentMotionComponent, otherEnvironmentComponent);
        }
    }
=======
        var tags1 = myEntityManager.getComponent(entity1, TagsComponent.class);
        var tags2 = myEntityManager.getComponent(entity2, TagsComponent.class);

        if (tags1 == null || tags2 == null)
            return tagPairs.toArray(new Pair[0]);

        for (String tag1 : tags1.getTags()) {
            for (String tag2 : tags2.getTags()) {
                var tagPair = new Pair(tag1, tag2);
                if (myCollisionResponses.containsKey(tagPair))
                    tagPairs.add(tagPair);
            }
        }

        return tagPairs.toArray(new Pair[0]);
    }

    private void handleEnvironments(Integer current, Integer other) {
        myCurrentCollisions.putIfAbsent(current, new HashSet<>());
        myCurrentCollisions.get(current).add(other);
        var currentMotionComponent = myEntityManager.getComponent(current, MotionComponent.class);
        var otherEnvironmentComponent = myEntityManager.getComponent(current, EnvironmentComponent.class);
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc

        if (currentMotionComponent == null || otherEnvironmentComponent == null)
            return;

        if (myPreviousCollisions.containsKey(current) && !myPreviousCollisions.get(current).contains(other))
            setInEnvironment(current, currentMotionComponent, otherEnvironmentComponent);
    }

<<<<<<< HEAD
    private void activateEvents(EngineInstance engineInstance1, EngineInstance engineInstance2, String responses) {
        GroovyShell shell = new GroovyShell(mySetter);
        mySetter.setProperty(COLLISION_KEYWORD1, engineInstance1);
        mySetter.setProperty(COLLISION_KEYWORD2, engineInstance2);
        Script script = shell.parse(responses);
        script.run();
=======
    private void setInEnvironment(Integer entity, MotionComponent motion, EnvironmentComponent environment) {
        myEntityCurrentEnvironments.put(entity, environment);

        double scaleFactor = environment.getVelDamper();
        motion.setXVelocity(scaleFactor * motion.getXVelocity());
        motion.setYVelocity(scaleFactor * motion.getYVelocity());
    }
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc

    //TODO fix if Triggers.Events are changed
    private void activateEvents(Integer current, Integer other, String responses) {
            //FIXME delegate rest of method to ObjectEvent/GameEvent and uncomment code above
            GroovyShell shell = new GroovyShell(mySetter);
            mySetter.setProperty("ID", current);
            mySetter.setProperty("otherID", other);
            Script script = shell.parse(responses);
            script.run();
    }

    public void addCollision(String type1, String type2, String response1, String response2){
        myCollisionResponses.put(new Pair<>(type1, type2), new Pair<>(response1, response2));
    }
}
