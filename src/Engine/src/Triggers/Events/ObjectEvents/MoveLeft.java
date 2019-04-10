package Engine.src.Triggers.Events.ObjectEvents;

import Triggers.Conditionals.Conditional;
import ECS.EntityManager;
import Triggers.Events.Event;

import java.util.List;

public class MoveLeft extends ObjectEvent {

    public MoveLeft() {super();}
    public MoveLeft(int obj){super(obj);}
    public MoveLeft(List<Conditional> conditionals){super(conditionals);}
    public MoveLeft(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) {
        entityManager.moveHorizontal(myObject, false);
    }

    @Override
    public Event copy() {
        return new MoveLeft(copyConditionals(), myObject);
    }
}
