package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Events.Event;

import java.util.List;

public class MoveUp extends ObjectEvent {

    public MoveUp() {super();}
    public MoveUp(int obj){super(obj);}
    public MoveUp(List<Conditional> conditionals){super(conditionals);}
    public MoveUp(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) {
        entityManager.moveVertical(myObject, false);
    }

    @Override
    public Event copy() {
        return new MoveUp(copyConditionals(), myObject);
    }
}