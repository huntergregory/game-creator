package Engine.src.Manager.Events.Motion;

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
    protected void modifyComponents(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        var motionComponent = instance.getComponent(MotionComponent.class);
        double stepTime = (double) args[0];
        double yVel = motionComponent.getMovementYVelocity();
        double yPos = basicComponent.getY();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetYPosition setY = new SetYPosition(myInstances, components);
        setY.activate(instance,yPos - yVel * stepTime);
    }
}
