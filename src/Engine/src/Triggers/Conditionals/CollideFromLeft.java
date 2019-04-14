package Engine.src.Triggers.Conditionals;

import Engine.src.ECS.CollisionDetector;
import Engine.src.ECS.EntityManager;

public class CollideFromLeft extends ObjectConditional{

    CollideFromLeft(boolean required, int obj){
        super(required, obj);
    }

    @Override
    public boolean satisfied(int other, EntityManager entityManager) {
        CollisionDetector collisionDetector = new CollisionDetector(entityManager);
        return collisionDetector.collideFromLeft(myObject, other);
    }

    @Override
    public boolean satisfied(EntityManager entityManager){
        return false; //TODO:error
    }

    @Override
    public Conditional copy() {
        return new CollideFromLeft(Required, myObject);
    }
}
