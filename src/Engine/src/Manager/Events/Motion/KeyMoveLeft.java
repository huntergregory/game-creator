package Engine.src.Manager.Events.Motion;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class KeyMoveLeft extends MotionEvent {
    public KeyMoveLeft(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xPos = basicComponent.getX();
        double stepTime = (double) args[0];
        double xVel = motionComponent.getMovementXVelocity();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myInstances, components);
        setX.activate(instance, xPos - xVel * stepTime);
    }

}
