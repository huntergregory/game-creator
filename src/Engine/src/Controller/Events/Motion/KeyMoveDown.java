package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Map;
import java.util.Set;

public class KeyMoveDown extends MotionEvent {

    public KeyMoveDown(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double yPos = basicComponent.getY();
        double yVel = motionComponent.getMovementYVelocity();
        SetYPosition setY = new SetYPosition(myEngineInstances);
        setY.activate(engineInstance,stepTime, yPos + yVel * stepTime);
    }
}
