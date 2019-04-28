package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class Stop extends MotionEvent {
    public Stop(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        var motionComponent = instance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity(0);
        motionComponent.setYVelocity(0);
    }
}
