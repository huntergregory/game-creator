package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Map;
import java.util.Set;

public class SetXVelocity extends MotionEvent {
    public SetXVelocity(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity((double) args[0]);
    }
}
