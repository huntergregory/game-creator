package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Events.Event;

import java.util.List;

public class Jump extends ObjectEvent {

    public Jump() {
        super();
    }
    public Jump(int obj){super(obj);}
    public Jump(List<Conditional> conditionals){super(conditionals);}
    public Jump(List<Conditional> conditionals, int obj){
        super(conditionals, obj);
    }

    @Override
    public void activate(EntityManager entityManager) {
        entityManager.jump(myObject);
    }

    @Override
    public Event copy() {
        return new Jump(copyConditionals(), myObject);
    }
}
