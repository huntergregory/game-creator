package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to move an instance down by key event based on it's MotionComponent
 * @author David Liu
 */
public class KeyMoveDown extends MotionEvent {

    public KeyMoveDown(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double yPos = basicComponent.getY();
        double yVel = motionComponent.getMovementYVelocity();
        SetYPosition setY = new SetYPosition(myEngineInstances, myGameEngineObjects);
        setY.activate(engineInstance,stepTime, yPos + yVel * stepTime);
    }
}
