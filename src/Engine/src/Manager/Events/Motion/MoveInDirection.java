package Engine.src.Manager.Events.Motion;

import Engine.src.Manager.Events.Motion.MotionEvent;
import Engine.src.Manager.Events.Motion.SetXPosition;
import Engine.src.Manager.Events.Motion.SetYPosition;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class MoveInDirection extends MotionEvent {
    public MoveInDirection(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses, Double.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        double tempVel = motionComponent.getMovementVelocity();
        double tempXVel = tempVel * (double) args[0];
        double tempYVel = tempVel * (double) args[1];
        double stepTime = (double) args[2];
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myInstances, components);
        SetYPosition setY = new SetYPosition(myInstances, components);
        setX.activate(instance, tempXVel * stepTime);
        setY.activate(instance, tempYVel * stepTime);
    }
}

