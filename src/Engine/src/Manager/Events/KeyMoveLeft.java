package Engine.src.Manager.Events;

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
    protected void modifyComponent(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        var motionComponent = instance.getComponent(MotionComponent.class);
        double xPos = basicComponent.getX();
        double xVel = motionComponent.getMovementXVelocity();
        setX(xPos - xVel * myStepTime);
    }
}
