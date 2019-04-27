package Engine.src.DeprecatedTriggers.Events.ObjectEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.DeprecatedTriggers.Events.Event;

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
