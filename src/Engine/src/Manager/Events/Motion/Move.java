package Engine.src.Manager.Events.Motion;

import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class Move extends MotionEvent {
    public Move(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        double stepTime = (double) args[0];
        double newX = getNewX(instance, basicComponent.getX(), stepTime);
        double newY = getNewY(instance, basicComponent.getY(), stepTime);
        basicComponent.setX(newX);
        basicComponent.setY(newY);
    }
}
