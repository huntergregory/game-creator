package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class Move extends MotionEvent {

    public Move(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double newX = getNewX(engineInstance, basicComponent.getX(), stepTime);
        double newY = getNewY(engineInstance, basicComponent.getY(), stepTime);
        basicComponent.setX(newX);
        basicComponent.setY(newY);
    }
}
