package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Events.Event;

import java.util.List;

public class GivePowerup extends ObjectEvent {

    public GivePowerup(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) {
       entityManager.addPowerup(myObject, myOther);
    }

    @Override
    public Event copy() {
        return new GivePowerup(copyConditionals(), myObject);
    }
}
