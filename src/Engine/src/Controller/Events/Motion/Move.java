package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to move an instance not from key inputs based on it's MotionComponent
 * @author David Liu
 */
public class Move extends MotionEvent {

    public Move(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class});
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
