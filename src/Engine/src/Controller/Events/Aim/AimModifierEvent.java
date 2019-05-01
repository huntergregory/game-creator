package Engine.src.Controller.Events.Aim;

import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.AimComponent;

import java.util.Map;
import java.util.Set;

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
