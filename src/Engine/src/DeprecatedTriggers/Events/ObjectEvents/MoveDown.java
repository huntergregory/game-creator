package Engine.src.DeprecatedTriggers.Events.ObjectEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.DeprecatedTriggers.Events.Event;

import java.util.List;

public class MoveDown extends ObjectEvent {

    public MoveDown() {super();}
    public MoveDown(int obj){super(obj);}
    public MoveDown(List<Conditional> conditionals){super(conditionals);}
    public MoveDown(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) {
        entityManager.moveVertical(myObject, true);
    }

    @Override
    public Event copy() {
        return new MoveDown(copyConditionals(), myObject);
    }
}