package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class RotateClockwise extends AimModifierEvent {
    public RotateClockwise(Set<Instance> instances) {
        super(instances);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        rotateAim(instance, true);
    }
}
