package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class Move extends MotionEvent {
    public Move(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        double newX = getNewX(instance, basicComponent.getX());
        double newY = getNewY(instance, basicComponent.getY());
        basicComponent.setX(newX);
        basicComponent.setY(newY);
    }
}
