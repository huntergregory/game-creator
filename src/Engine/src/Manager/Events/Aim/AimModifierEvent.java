package Engine.src.Manager.Events.Aim;

import Engine.src.EngineData.Components.AimComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Manager.Events.ComponentDependentEvent;

import java.util.Map;

public abstract class AimModifierEvent extends ComponentDependentEvent {
    public AimModifierEvent(Map<String, EngineInstance> engineInstances, Class<?> ... parameterTypes) {
        super(engineInstances, AimComponent.class, parameterTypes);
    }

    protected void rotateAim(EngineInstance engineInstance, boolean clockwise){
        AimComponent aim = engineInstance.getComponent(AimComponent.class);
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
