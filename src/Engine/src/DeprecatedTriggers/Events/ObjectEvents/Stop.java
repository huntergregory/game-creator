package Engine.src.DeprecatedTriggers.Events.ObjectEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.DeprecatedTriggers.Events.Event;

import java.util.List;

public class Stop extends ObjectEvent {

    public Stop(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) { entityManager.stop(myObject);}

    @Override
    public Event copy() {
        return new Stop(copyConditionals(), myObject);
    }
}
