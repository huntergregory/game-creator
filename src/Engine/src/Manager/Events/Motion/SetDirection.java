package Engine.src.Manager.Events.Motion;

import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetDirection extends MotionEvent {
    public SetDirection(Set<Instance> instances) {
        super(instances, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        double angle = (double) args[0];
        adjustVelocitiesByAngle(instance, angle);
    }
}
