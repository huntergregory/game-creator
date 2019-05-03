package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Events.Event;

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
