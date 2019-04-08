package Conditionals;

import ECS.CollisionDetector;
import ECS.EntityManager;
import ECS.NoEntityException;
import GameObjects.GameObject;
import GameObjects.ObjectManager;
import Physics.CollisionHandler;

public class CollideFromBottom extends ObjectConditional {

    CollideFromBottom(boolean required, int obj){
        super(required, obj);
    }

    @Override
    public boolean satisfied(int other, EntityManager entityManager){
        CollisionDetector collisionDetector = new CollisionDetector(entityManager);
        try {
            return collisionDetector.collideFromTop(other, myObject);
        }
        catch(NoEntityException e){
            e.getMessage();
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