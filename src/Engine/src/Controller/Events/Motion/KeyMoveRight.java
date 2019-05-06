package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to move an instance right by key event based on it's MotionComponent
 * @author David Liu
 */
public class KeyMoveRight extends MotionEvent {

    public KeyMoveRight(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double xVel = motionComponent.getMovementXVelocity();
        double xPos = basicComponent.getX();
        SetXPosition setX = new SetXPosition(myEngineInstances, myGameEngineObjects);
        setX.activate(engineInstance, stepTime, xPos + xVel * stepTime);
    }
}
