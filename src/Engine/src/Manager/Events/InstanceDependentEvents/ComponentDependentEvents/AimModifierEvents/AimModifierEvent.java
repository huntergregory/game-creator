package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents.AimModifierEvents;

import Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents.ComponentDependentEvent;
import gamedata.Game;
import gamedata.GameObjects.Components.AimComponent;
import gamedata.GameObjects.Instance;

public abstract class AimModifierEvent extends ComponentDependentEvent {
    public AimModifierEvent(Game game, Class<?> ... parameterTypes) {
        super(game, AimComponent.class, parameterTypes);
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
