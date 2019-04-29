package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Set;

public class SetXVelocity extends MotionEvent {
    public SetXVelocity(Set<EngineInstance> engineInstances) {
        super(engineInstances, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity((double) args[0]);
    }
}
