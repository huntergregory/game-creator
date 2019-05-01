package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class KeyMoveUp extends MotionEvent {

    public KeyMoveUp(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double yVel = motionComponent.getMovementYVelocity();
        double yPos = basicComponent.getY();
        SetYPosition setY = new SetYPosition(myEngineInstances);
        setY.activate(engineInstance,yPos - yVel * stepTime);
    }
}
