package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetYVelocity extends MotionEvent {
    public SetYVelocity(Set<Instance> instances) {
        super(instances, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity((double) args[0]);
    }

}
