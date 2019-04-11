package Triggers.Events.ObjectEvents;

import Triggers.Conditionals.Conditional;
import ECS.EntityManager;

import java.util.List;

public class Deflect extends DirectionChange{

    double myAdjustmentAngle;

    public Deflect(List<Conditional> conditionals, int angle){
        super(conditionals,angle);
    }

    @Override
    public void activate(EntityManager entityManager){
        entityManager.setYVelocity(myObject, -25);//FIXME modifiying for demo
        //entityManager.adjustDirection(myObject, myAdjustmentAngle);
    }
}
