package Engine.src.Controller.Events.Aim;

import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.AimComponent;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public abstract class AimModifierEvent extends ComponentDependentEvent {
    public AimModifierEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<?> ... parameterTypes) {
        super(engineInstances, engineObjects, AimComponent.class, parameterTypes);
    }

    protected void rotateAim(EngineInstance engineInstance, boolean clockwise){
        AimComponent aim = engineInstance.getComponent(AimComponent.class);
        double aimX = aim.getXAim();
        double aimY = aim.getYAim();
        double[] vec = {aimX, aimY};
        double currentAngle = calculateAngle(vec);
        double newAngle;
        if(clockwise)
            newAngle = currentAngle + aim.getRotationRate();
        else
            newAngle = currentAngle - aim.getRotationRate();
        aim.setXAim(Math.cos(newAngle));
        aim.setYAim(Math.sin(newAngle));
    }

}
