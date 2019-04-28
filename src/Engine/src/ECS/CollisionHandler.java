package Engine.src.ECS;

import Engine.src.Manager.Events.Motion.Move;
import Engine.src.Manager.Manager;
import gamedata.GameObjects.Components.*;

import Engine.src.Controller.LevelManager;

import gamedata.GameObjects.Instance;
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
    private Manager myManager;
    private CollisionDetector myCollisionDetector;
    private Map<Pair<String>, Pair<String>> myCollisionResponses;
    private Map<Instance, Set<Instance>> myPreviousCollisions;
    private Map<Instance, Set<Instance>> myCurrentCollisions;
    private Map<Instance, EnvironmentComponent> myEntityCurrentEnvironments;
    private Binding mySetter;

    //FIXME
    private static final double MY_DEFAULT_ACCEL_Y = 5;
    private static final double MY_DEFAULT_ACCEL_X = 0;
    //FIXME

    public CollisionHandler(Manager manager) {
        myManager = manager;
        myCollisionResponses = new HashMap<>();
        myPreviousCollisions = new HashMap<>();
        myCurrentCollisions = new HashMap<>();
        myEntityCurrentEnvironments = new HashMap<>();
        myCollisionDetector = new CollisionDetector();
        mySetter = new Binding();
        mySetter.setProperty("manager", myManager);
        mySetter.setProperty("collisionDetector", myCollisionDetector);
    }

    //assumes collisionResponses and entities are nonnull
    public void handleCollisions(Set<Instance> allEntities, Map<Pair<String>, Pair<String>> collisionResponses) {
        myCollisionResponses = collisionResponses;
        myCurrentCollisions = new HashMap<>();

        moveThenUpdateVelocities(allEntities);

        List<Instance> allEntitiesList = new ArrayList<Instance>();
        allEntitiesList.addAll(allEntities);

        for (int k = 0; k < allEntitiesList.size(); k++) {
            for (int j = 0; j < allEntitiesList.size(); j++) {
                if (k >= j) {
                    continue;
                }
                checkCollision(allEntitiesList.get(k), allEntitiesList.get(j));
            }
        }

        for (Instance entity : allEntities) {
            if (notInteractingWithEnvironment(entity))
                setInDefaultEnvironment(entity);
        }
        myPreviousCollisions = myCurrentCollisions;
    }

    private void moveThenUpdateVelocities(Set<Instance> allEntities) {
        for (Instance i : allEntities){
            var motionComponent = i.getComponent(MotionComponent.class);
            if (motionComponent != null) {
                //myEntityManager.move(id);
                //motionComponent.updateVelocity();

                //myManager.call(Move.class, i);
                //myManager.call(Move.class, i)
            }
        }
    }

    private boolean notInteractingWithEnvironment(Instance i) {
        if (myCurrentCollisions.containsKey(i)) {
            Set<Instance> possibleEnvironments = myCurrentCollisions.get(i);
            for (Instance possibleEnvironment : possibleEnvironments) {
                if (possibleEnvironment.getComponent(EnvironmentComponent.class) != null)
                    return true;
            }
        }
        return false;
    }

    private void setInDefaultEnvironment(Instance i) {
        myEntityCurrentEnvironments.put(i, null);
        var motion = i.getComponent(MotionComponent.class);
        if (motion != null) {
            //motion.setXAcceleration(MY_DEFAULT_ACCEL_X);
            //motion.setYAcceleration(MY_DEFAULT_ACCEL_Y);
        }
    }

    //FIXME Directional collisional logic
    private void checkCollision(Instance i, Instance j) {
        Pair<String>[] collisionTagPairs = findRelevantTagPairs(i, j);
        if (!myCollisionDetector.collides(i, j))
            return;
        if (collisionTagPairs.length != 0 ) {

            handleEnvironments(i, j);
            handleEnvironments(j, i);

            for (Pair<String> tagPair : collisionTagPairs) {
                Pair<String> responseListPair = myCollisionResponses.get(tagPair);
                activateEvents(i, j, responseListPair.getItem1());
                activateEvents(j, i, responseListPair.getItem2());
            }
        }
        correctClipping(i, j);
        correctClipping(j, i);
        dealWithImpassable(i, j);
        dealWithImpassable(j, i);
    }

    //FIXME account for movement velocities in motioncomponent - save keyinputs for movement portion of collisionhandler
    private boolean correctClipping(Instance toAdjust, Instance other) {
        var environmentComponent = other.getComponent(EnvironmentComponent.class);
        var motionComponent = toAdjust.getComponent(MotionComponent.class);
        var adjBasic = toAdjust.getComponent(BasicComponent.class);
        var otherBasic = other.getComponent(BasicComponent.class);
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
    private void dealWithImpassable(Instance mover, Instance impassable) {
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

    private Pair<String>[] findRelevantTagPairs(Instance i, Instance j) {
        ArrayList<Pair<String>> tagPairs = new ArrayList<>();

        var tags1 = i.getComponent(TagsComponent.class);
        var tags2 = j.getComponent(TagsComponent.class);

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

    private void handleEnvironments(Instance current, Instance other) {
        myCurrentCollisions.putIfAbsent(current, new HashSet<>());
        myCurrentCollisions.get(current).add(other);
        var currentMotionComponent = current.getComponent(MotionComponent.class);
        var otherEnvironmentComponent = current.getComponent(EnvironmentComponent.class);

        if (currentMotionComponent == null || otherEnvironmentComponent == null)
            return;

        if (myPreviousCollisions.containsKey(current) && !myPreviousCollisions.get(current).contains(other))
            setInEnvironment(current, currentMotionComponent, otherEnvironmentComponent);
    }

    private void setInEnvironment(Instance i, MotionComponent motion, EnvironmentComponent environment) {
        myEntityCurrentEnvironments.put(i, environment);

        double scaleFactor = environment.getVelDamper();
        motion.setXVelocity(scaleFactor * motion.getXVelocity());
        motion.setYVelocity(scaleFactor * motion.getYVelocity());
    }

    //TODO fix if Timers.Events are changed
    private void activateEvents(Instance current, Instance other, String responses) {
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
