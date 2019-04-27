package Engine.src.Triggers.Events.ObjectEvents;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Events.Event;

import java.util.List;

public class Deflect extends DirectionChange{

    double myAdjustmentAngle;

    public Deflect(List<Conditional> conditionals, double angle) {
        super(conditionals, 180);
    }

    @Override
    public void activate(EntityManager entityManager){
        entityManager.setYVelocity(myObject, -25);//FIXME modifiying for demo
        //entityManager.adjustDirection(myObject, myAdjustmentAngle);
    }

    @Override
    public Event copy() {
        return new Deflect(copyConditionals(), myAdjustmentAngle);
    }
}
