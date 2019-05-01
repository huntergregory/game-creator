package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class Stop extends MotionEvent {
    public Stop(Set<EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity(0);
        motionComponent.setYVelocity(0);
    }
}
