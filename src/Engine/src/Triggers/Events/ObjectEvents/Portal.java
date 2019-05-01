package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Events.Event;

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

    @Override
    public Event copy() {
        return new Portal(copyConditionals(), myShoot);
    }
}
