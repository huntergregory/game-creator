package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class KeyMoveLeft extends MotionEvent {

    public KeyMoveLeft(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class});
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double xPos = basicComponent.getX();
        double xVel = motionComponent.getMovementXVelocity();
        SetXPosition setX = new SetXPosition(myEngineInstances, myGameEngineObjects);
        setX.activate(engineInstance, stepTime,xPos - xVel * stepTime);
    }

}
