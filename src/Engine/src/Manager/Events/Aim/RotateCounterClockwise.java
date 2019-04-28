package Engine.src.Manager.Events.Aim;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class RotateCounterClockwise extends AimModifierEvent {
    public RotateCounterClockwise(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        rotateAim(instance, false);
    }
}
