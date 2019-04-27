package Engine.src.ECS;

import Engine.src.Components.*;

import Engine.src.Controller.LevelManager;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;

import static java.lang.Math.abs;

/**
 * FIXME
 * Bugs: default gravity etc not set
 *       impassables don't allow movement at all
 */
public class CollisionHandler {
    private EntityManager myEntityManager;
    private LevelManager myLevelManager;
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

        moveThenUpdateVelocities(entities);

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

    private void moveThenUpdateVelocities(Set<Integer> entities) {
        for (int id : entities){
            var motionComponent = myEntityManager.getComponent(id, MotionComponent.class);
            if (motionComponent != null) {
                myEntityManager.move(id);
                motionComponent.updateVelocity();
            }
        }
    }

    private boolean notInteractingWithEnvironment(Integer entity) {
        if (myCurrentCollisions.containsKey(entity)) {
            Set<Integer> possibleEnvironments = myCurrentCollisions.get(entity);
            for (Integer possibleEnvironment : possibleEnvironments) {
                if (myEntityManager.getComponent(possibleEnvironment, EnvironmentComponent.class) != null)
                    return true;
            }
        }
        return false;
    }

    private void setInDefaultEnvironment(Integer entity) {
        myEntityCurrentEnvironments.put(entity, null);
        var motion = myEntityManager.getComponent(entity, MotionComponent.class);
        if (motion != null) {
            motion.setXAcceleration(MY_DEFAULT_ACCEL_X);
            motion.setYAcceleration(MY_DEFAULT_ACCEL_Y);
        }
    }

    //FIXME Directional collisional logic
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
        correctClipping(entity1, entity2);
        correctClipping(entity2, entity1);
        dealWithImpassable(entity1, entity2);
        dealWithImpassable(entity2, entity1);
    }

    //FIXME account for movement velocities in motioncomponent - save keyinputs for movement portion of collisionhandler
    private boolean correctClipping(Integer toAdjust, Integer other) {
        var environmentComponent = myEntityManager.getComponent(other, EnvironmentComponent.class);
        var motionComponent = myEntityManager.getComponent(toAdjust, MotionComponent.class);
        var adjBasic = myEntityManager.getComponent(toAdjust, BasicComponent.class);
        var otherBasic = myEntityManager.getComponent(other, BasicComponent.class);
        if (environmentComponent == null && motionComponent != null && adjBasic != null && otherBasic != null) {
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
        return false;
    }

    //FIXME duplicated between parts of collision handler and parts of Entity manager
    private void dealWithImpassable(Integer mover, Integer impassable) {
        var impassableComponent = myEntityManager.getComponent(impassable, ImpassableComponent.class);
        if (impassableComponent != null && impassableComponent.getImpassable()) {
            var motion = myEntityManager.getComponent(mover, MotionComponent.class);
            if (motion == null)
                return;
            //motion.setYVelocity(0);
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

    private Pair<String>[] findRelevantTagPairs(Integer entity1, Integer entity2) {
        ArrayList<Pair<String>> tagPairs = new ArrayList<>();

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

        if (currentMotionComponent == null || otherEnvironmentComponent == null)
            return;

        if (myPreviousCollisions.containsKey(current) && !myPreviousCollisions.get(current).contains(other))
            setInEnvironment(current, currentMotionComponent, otherEnvironmentComponent);
    }

    private void setInEnvironment(Integer entity, MotionComponent motion, EnvironmentComponent environment) {
        myEntityCurrentEnvironments.put(entity, environment);

        double scaleFactor = environment.getVelDamper();
        motion.setXVelocity(scaleFactor * motion.getXVelocity());
        motion.setYVelocity(scaleFactor * motion.getYVelocity());
    }

    //TODO fix if Timers.Events are changed
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
