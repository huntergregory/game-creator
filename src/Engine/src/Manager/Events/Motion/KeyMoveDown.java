package Engine.src.Manager.Events.Motion;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class KeyMoveDown extends MotionEvent {
    public KeyMoveDown(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        var basicComponent = instance.getComponent(BasicComponent.class);
        double stepTime = (double) args[0];
        double yPos = basicComponent.getY();
        double yVel = motionComponent.getMovementYVelocity();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetYPosition setY = new SetYPosition(myInstances, components);
        setY.activate(instance,yPos + yVel * stepTime);
    }
}
