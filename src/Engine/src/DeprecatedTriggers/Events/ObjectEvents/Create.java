package Engine.src.DeprecatedTriggers.Events.ObjectEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import gamedata.Components.Component;
import Engine.src.ECS.EntityManager;
import Engine.src.DeprecatedTriggers.Events.Event;

import java.util.List;
import java.util.Map;

public class Create extends ObjectEvent{

    Map<Class<? extends Component>, Component> myComponents;

    public Create(int obj, Map<Class<? extends Component>, Component> components){
        super(obj);
        myComponents = components;
    }

    public Create(List<Conditional> conditionals, int obj, Map<Class<? extends Component>, Component> components){
        super(conditionals, obj);
        myComponents = components;
    }

    @Override
    public void activate(EntityManager entityManager) {
        entityManager.create(myObject, myComponents);
    }

    @Override
    public Event copy() {
        return new Create(copyConditionals(), myObject, myComponents);
    }
}
