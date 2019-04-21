package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Events.Event;

import java.util.List;

@Deprecated
public abstract class ObjectEvent extends Event {

    protected int myObject;
    protected int myOther;

    public ObjectEvent(){
        super();
    }

    public ObjectEvent(int obj){
        super();
        myObject = obj;
        myOther = -1;
    }

    public ObjectEvent(List<Conditional> conditionals){
        super(conditionals);
        myObject = -1;
        myOther = -1;
    }

    public ObjectEvent(List<Conditional> conditionals, int obj){
        super(conditionals);
        myObject = obj;
        myOther = -1;
    }

    public void setEventObject(int obj){
        if(myObject == -1) myObject = obj;
    }

    public abstract void activate(EntityManager entityManager);

    public void setOther(int other){
        if(myOther == -1) myOther = other;
    }

    @Override
    public abstract Event copy();
}
