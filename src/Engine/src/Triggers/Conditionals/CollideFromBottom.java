package Engine.src.Triggers.Conditionals;

import Engine.src.ECS.CollisionDetector;
import Engine.src.ECS.EntityManager;

public class CollideFromBottom extends ObjectConditional {

    CollideFromBottom(boolean required, int obj){
        super(required, obj);
    }

    @Override
    public boolean satisfied(int other, EntityManager entityManager) {
        CollisionDetector collisionDetector = new CollisionDetector(entityManager);
        return collisionDetector.collideFromTop(other, myObject);
    }

    @Override
    public boolean satisfied(EntityManager entityManager){
        return false; //TODO:error
    }

    @Override
    public Conditional copy() {
        return new CollideFromBottom(Required, myObject);
    }
}