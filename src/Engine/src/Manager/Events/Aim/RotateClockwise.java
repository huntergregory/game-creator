package Engine.src.Manager.Events.Aim;

import gamedata.GameObjects.Instance;

import java.util.Set;

public class RotateClockwise extends AimModifierEvent{

    public RotateClockwise(Set<Instance> instanceSet){
        super(instanceSet);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        rotateAim(instance, true);
    }
}
