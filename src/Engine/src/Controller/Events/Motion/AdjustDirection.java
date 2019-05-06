package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to adjust the direction of an instance based on it's MotionComponent
 * @author David Liu
 */
public class AdjustDirection extends MotionEvent {
    public AdjustDirection(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        double angle = getAngle(engineInstance) + (double) args[0];
        adjustVelocitiesByAngle(engineInstance, angle);
    }
}
