package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.JumpComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class Jump extends MotionEvent {
    public Jump(Set<EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, JumpComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        var jumpComponent = engineInstance.getComponent(JumpComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double jumpVelocity = jumpComponent.getJumpVelocity();
        motionComponent.setYVelocity(jumpVelocity);
    }
}
