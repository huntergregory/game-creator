package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.JumpComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to make an instance jump based on it's MotionComponent
 * @author David Liu
 */
public class Jump extends MotionEvent {

    public Jump(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, JumpComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var jumpComponent = engineInstance.getComponent(JumpComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double jumpVelocity = jumpComponent.getJumpVelocity();
        motionComponent.setYVelocity(jumpVelocity);
    }
}
