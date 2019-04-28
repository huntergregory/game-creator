package Engine.src.Manager.Events.Motion;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class KeyMoveRight extends MotionEvent {
    public KeyMoveRight(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        var basicComponent = instance.getComponent(BasicComponent.class);
        double xVel = motionComponent.getMovementXVelocity();
        double xPos = basicComponent.getX();
        double stepTime = (double) args[0];
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myInstances, components);
        setX.activate(instance, xPos - xVel * stepTime);
    }
}
