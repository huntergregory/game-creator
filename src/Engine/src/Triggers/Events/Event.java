package Engine.src.Triggers.Events;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Conditionals.ObjectConditional;
import Engine.src.ECS.EntityManager;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class Event {
    protected List<Conditional> myConditionals;

    public Event(){
        myConditionals = new ArrayList<>();
    }

    public Event(List<Conditional> conditionals){
        myConditionals = conditionals;
    }

    public abstract Event copy();

    protected List<Conditional> copyConditionals(){
        List<Conditional> conditionalsCopy = new ArrayList<>();
        for(Conditional conditional: myConditionals){
            conditionalsCopy.add(conditional.copy());
        }
        return conditionalsCopy;
    }

    public void setConditionalObject(int obj){
        for(Conditional conditional: myConditionals){
            if (conditional instanceof ObjectConditional){
                if (((ObjectConditional) conditional).getMyObj() == -1) ((ObjectConditional) conditional).setMyObject(obj);
            }
            }
    }

    public boolean conditionsSatisfied(int obj, EntityManager entityManager){
        for (Conditional conditional : myConditionals){
            if (!conditional.satisfied(obj, entityManager) && conditional.required()) return false;
        }
        return true;
    }

    public boolean conditionsSatisfied(EntityManager entityManager){
        for (Conditional conditional : myConditionals){
            if (!conditional.satisfied(entityManager) && conditional.required()) return false;
        }
        return true;
    }

    public List<Conditional> getConditionals() {
        return myConditionals;
    }
}
