package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class AdjustDirection extends MotionEvent {
    public AdjustDirection(Set<Instance> instances) {
        super(instances, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        double angle = getAngle(instance) + (double) args[0];
        adjustVelocitiesByAngle(instance, angle);
    }
}
