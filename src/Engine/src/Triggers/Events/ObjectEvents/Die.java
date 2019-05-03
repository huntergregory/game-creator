package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Events.Event;

import java.util.List;

    public class Die extends ObjectEvent {

        public Die() {super();}
        public Die(int obj){super(obj);}
        public Die(List<Conditional> conditionals){super(conditionals);}
        public Die(List<Conditional> conditionals, int obj){
            super(conditionals, obj);
        }


        @Override
        public void activate(EntityManager entityManager) { entityManager.die(myObject);}

        @Override
        public Event copy() {
            return new Die(copyConditionals(), myObject);
        }
    }


