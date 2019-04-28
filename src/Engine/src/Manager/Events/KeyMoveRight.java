package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class KeyMoveRight extends MotionEvent {
    public KeyMoveRight(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        var basicComponent = instance.getComponent(BasicComponent.class);
        double xVel = motionComponent.getMovementXVelocity();
        double xPos = basicComponent.getX();
        setX(xPos + xVel * myStepTime);
    }
}
