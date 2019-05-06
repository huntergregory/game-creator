package Engine.src.ECS;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.ImpassableComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.ArrayList;
import java.util.Map;

/**
 * This is a class used to detect collisions from all four sides and get a list of impassable colliders
 * @author David Liu and Jonathan Yu
 */
public class CollisionDetector {
    private final double BUFFER_HORIZ = 6;
    private final double BUFFER_VERT = 10;
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double width1;
    private double width2;
    private double height1;
    private double height2;

    /**
     * Gives the list of other impassable colliders for a specific EngineInstance, not including itself
     * @param entity an EngineInstance object
     * @param allEntities Map of EngineInstance ids (Strings) with the EngineInstances themselves
     * @return list of impassable colliders not including the entity that it is compared to
     */
    public ArrayList<EngineInstance> getImpassableColliders(EngineInstance entity, Map<String, EngineInstance> allEntities) {
        ArrayList<EngineInstance> impassables = new ArrayList<>();

        for (String ID : allEntities.keySet()) {
            EngineInstance other = allEntities.get(ID);
            if (other.equals(entity))
                continue;
            if (other.hasComponent(ImpassableComponent.class)) {
                var impassableComponent = other.getComponent(ImpassableComponent.class);
                if (impassableComponent != null && impassableComponent.getImpassable())
                    impassables.add(other);
            }
        }
        return impassables;
    }

    /**
     * Gives the result of whether the EngineInstances are colliding
     * @param collider an EngineInstance object that is initiating colliding
     * @param target an EngineInstance object that is the one colliding with
     * @return the boolean of whether the EngineInstances are colliding
     */
    public boolean collides(EngineInstance collider, EngineInstance target) {
        return collideFromLeft(collider, target) ||
                collideFromLeft(target, collider) ||
                collideFromTop(collider, target) ||
                collideFromTop(target, collider);
    }

    /**
     * Gives the result of whether the collider is colliding from the left of the target
     * @param collider an EngineInstance object that is initiating colliding
     * @param target an EngineInstance object that is the one colliding with
     * @return the boolean of whether the collider is colliding from the left of the target
     */
    public boolean collideFromLeft(EngineInstance collider, EngineInstance target) {
        setCurrCollisionValues(collider, target);

        boolean overlapsFromLeft = x1 < x2 && x2 <= x1 + width1 && x1 + width1 <= x2 + width2;
        boolean overlapsVertically = !(y1 + height1 < y2 + BUFFER_VERT || y1 > y2 + height2 - BUFFER_VERT);
        return overlapsFromLeft && overlapsVertically;
    }

    /**
     * Gives the result of whether the collider is colliding from the top of the target
     * @param collider an EngineInstance object that is initiating colliding
     * @param target an EngineInstance object that is the one colliding with
     * @return the boolean of whether the collider is colliding from the top of the target
     */
    public boolean collideFromTop(EngineInstance collider, EngineInstance target) {
        setCurrCollisionValues(collider, target);

        boolean overlapsFromTop = y1 < y2 && y2 <= y1 + height1 && y1 + height1 <= y2 + height2;
        boolean overlapsHorizontally = !(x1 + width1 < x2 + BUFFER_HORIZ || x1 > x2 + width2 - BUFFER_HORIZ);
        return overlapsFromTop && overlapsHorizontally;
    }

    private void setCurrCollisionValues(EngineInstance collider, EngineInstance target) {
        var colliderComponent = collider.getComponent(BasicComponent.class);
        var targetComponent = target.getComponent(BasicComponent.class);
        x1 = colliderComponent.getX();
        x2 = targetComponent.getX();
        y1 = colliderComponent.getY();
        y2 = targetComponent.getY();
        width1 = colliderComponent.getWidth();
        width2 = targetComponent.getWidth();
        height1 = colliderComponent.getHeight();
        height2 = targetComponent.getHeight();
    }
}
