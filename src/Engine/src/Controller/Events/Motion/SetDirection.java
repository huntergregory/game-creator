package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class SetDirection extends MotionEvent {
    public SetDirection(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        double angle = (double) args[0];
        adjustVelocitiesByAngle(engineInstance, angle);
    }
}
