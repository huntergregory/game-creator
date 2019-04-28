package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class KeyMoveUp extends MotionEvent {
    public KeyMoveUp(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        var motionComponent = instance.getComponent(MotionComponent.class);
        double yVel = motionComponent.getMovementYVelocity();
        double yPos = basicComponent.getY();
        setX(yPos - yVel * myStepTime);
    }
}
