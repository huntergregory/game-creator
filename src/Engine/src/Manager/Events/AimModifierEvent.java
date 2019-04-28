package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.AimComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class AimModifierEvent extends ComponentDependentEvent {
    public AimModifierEvent(Set<Instance> instances, Class<?> ... parameterTypes) {
        super(instances, AimComponent.class, parameterTypes);
    }

    protected void rotateAim(Instance instance, boolean clockwise){
        AimComponent aim = instance.getComponent(AimComponent.class);
        double currentAngle = Math.atan(aim.getYAim()/aim.getXAim());
        double newAngle;
        if(clockwise)
            newAngle = currentAngle + aim.getRotationRate();
        else
            newAngle = currentAngle - aim.getRotationRate();
        aim.setXAim(Math.cos(newAngle));
        aim.setYAim(Math.sin(newAngle));
    }
}
