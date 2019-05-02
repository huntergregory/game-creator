package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.JumpComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class Jump extends MotionEvent {
    public Jump(Map<String, EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var jumpComponent = engineInstance.getComponent(JumpComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double jumpVelocity = jumpComponent.getJumpVelocity();
        motionComponent.setYVelocity(jumpVelocity);
    }
}
