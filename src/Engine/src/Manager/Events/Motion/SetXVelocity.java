package Engine.src.Manager.Events.Motion;

import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetXVelocity extends MotionEvent {
    public SetXVelocity(Set<Instance> instances) {
        super(instances, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity((double) args[0]);
    }
}
