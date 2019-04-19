package Engine.src.ECS;

import Engine.src.Components.EnvironmentComponent;
import Engine.src.Components.ImpassableComponent;
import Engine.src.Components.MotionComponent;
import Engine.src.Components.TagsComponent;
import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Conditionals.ObjectConditional;
import Engine.src.Controller.LevelManager;
import Engine.src.Triggers.Events.GameEvents.GameEvent;
import Engine.src.Triggers.Events.ObjectEvents.ObjectEvent;
import Engine.src.Triggers.Events.Event;

import java.util.*;

/**
 * FIXME
 * Bugs: default gravity etc not set
 *       impassables don't allow movement at all
 */
public class CollisionHandler {
    private EntityManager myEntityManager;
    private LevelManager myLevelManager;
    private CollisionDetector myCollisionDetector;
    private Map<Pair<String>, Pair<List<Event>>> myCollisionResponses;
    private Map<Integer, Set<Integer>> myPreviousCollisions;
    private Map<Integer, Set<Integer>> myCurrentCollisions;
    private Map<Integer, EnvironmentComponent> myEntityCurrentEnvironments;

    //FIXME
    private static final double MY_DEFAULT_ACCEL_Y = 5;
    private static final double MY_DEFAULT_ACCEL_X = 0;
    //FIXME

    public CollisionHandler(EntityManager objectManager, LevelManager levelManager, CollisionDetector collisionDetector) {
        myEntityManager = objectManager;
        myLevelManager = levelManager;
        myCollisionDetector = collisionDetector;
        myCollisionResponses = new HashMap<>();
        myPreviousCollisions = new HashMap<>();
        myCurrentCollisions = new HashMap<>();
        myEntityCurrentEnvironments = new HashMap<>();
    }

    //assumes collisionResponses and entities are nonnull
    public void dealWithCollisions(Set<Integer> entities, Map<Pair<String>, Pair<List<Event>>> collisionResponses) {
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

    private void checkCollision(Integer entity1, Integer entity2) {
        Pair<String>[] collisionTagPairs = findRelevantTagPairs(entity1, entity2);
        if (!myCollisionDetector.collides(entity1, entity2))
            return;
        if (collisionTagPairs.length != 0 ) {

            handleEnvironments(entity1, entity2);
            handleEnvironments(entity2, entity1);

            for (Pair<String> tagPair : collisionTagPairs) {
                Pair<List<Event>> responseListPair = myCollisionResponses.get(tagPair);
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

    //TODO fix if Triggers.Events are changed
    private void activateEvents(Integer current, Integer other, List<Event> responseList) {
        for (Event e : responseList) {
            /*if (event instanceof ObjectEvent) {                 //only include other if necessary for conditionals
                ((ObjectEvent) event).activate(current, other, myEntityManager);
            }
            if (event instanceof GameEvent) {
                ((GameEvent) event).activate(current, other, myLevelManager);
            }*/
            //FIXME delegate rest of method to ObjectEvent/GameEvent and uncomment code above
            Event event = e.copy();
            List<Conditional> conditionals = event.getConditionals();
            for (Conditional conditional : conditionals) {
                if (conditional instanceof ObjectConditional) {
                    event.setConditionalObject(current);
                }
            }
            if (event.conditionsSatisfied(other, myEntityManager)) {
                if (event instanceof ObjectEvent) {
                    ((ObjectEvent) event).setEventObject(current);
                    ((ObjectEvent) event).setOther(other);
                    ((ObjectEvent) event).activate(myEntityManager);
                }
                    else
                    ((GameEvent) event).activate(myLevelManager);
            }
        }
    }
}
