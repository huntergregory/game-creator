package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class SetYVelocity extends MotionEvent {
    public SetYVelocity(Set<EngineInstance> engineInstances) {
        super(engineInstances, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        motionComponent.setXVelocity((double) args[0]);
    }

}
