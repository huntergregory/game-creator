package Triggers.Events.ObjectEvents;

import ECS.EntityManager;
import Triggers.Conditionals.Conditional;

import java.util.List;

//FIXME added for demo
public class Portal extends ObjectEvent {

    private boolean myShoot;

    public Portal(List<Conditional> conditionals, boolean shoot){
        super(conditionals);
        myShoot = shoot;
    }

    @Override
    public void activate(EntityManager entityManager){

        if (myShoot)
            entityManager.setXVelocity(myObject, 50);
        else
            entityManager.setXVelocity(myObject, 0);

    }
}
