package Engine.src.ECS;

import gamedata.Components.BasicComponent;

import java.util.ArrayList;
import java.util.Set;

public class CollisionDetector {

    /**
     * Used for more precise directional collision detection when colliding at a corner (avoid colliding from two
     * directions at the same time)
     */
    private final double BUFFER_HORIZ = 6;
    private final double BUFFER_VERT = 10;

    private EntityManager myEntityManager;

    /**
     * Position and dimension data for a specific collision, where 1 is the collider and 2 is the target
     */
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double width1;
    private double width2;
    private double height1;
    private double height2;

    public CollisionDetector(EntityManager entityManager) {
        myEntityManager = entityManager;
    }

    public Integer[] getImpassableColliders(Integer entity, Set<Integer> allEntities) {
        ArrayList<Integer> impassables = new ArrayList<>();

        /*for (Integer other : allEntities) {
            if (other.equals(entity))
                continue;
            var impassableComponent = myEntityManager.getComponent(other, ImpassableComponent.class);
            if (impassableComponent != null && impassableComponent.getImpassable())
                impassables.add(other);
        }*/
        return impassables.toArray(new Integer[0]);
    }

    public boolean collides(Integer collider, Integer target) {
        return collideFromLeft(collider, target) ||
                collideFromLeft(target, collider) ||
                collideFromTop(collider, target) ||
                collideFromTop(target, collider);
    }

    public boolean collideFromLeft(Integer collider, Integer target) {
        setCurrCollisionValues(collider, target);

        boolean overlapsFromLeft = x1 < x2 && x2 <= x1 + width1 && x1 + width1 <= x2 + width2;
        boolean overlapsVertically = !(y1 + height1 < y2 + BUFFER_VERT || y1 > y2 + height2 - BUFFER_VERT);
        return overlapsFromLeft && overlapsVertically;
    }

    public boolean collideFromTop(Integer collider, Integer target) {
        setCurrCollisionValues(collider, target);

        boolean overlapsFromTop = y1 < y2 && y2 <= y1 + height1 && y1 + height1 <= y2 + height2;
        boolean overlapsHorizontally = !(x1 + width1 < x2 + BUFFER_HORIZ || x1 > x2 + width2 - BUFFER_HORIZ);
        return overlapsFromTop && overlapsHorizontally;
    }

    private void setCurrCollisionValues(Integer collider, Integer target) {
        var colliderComponent = myEntityManager.getComponent(collider, BasicComponent.class);
        var targetComponent = myEntityManager.getComponent(target, BasicComponent.class);
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
