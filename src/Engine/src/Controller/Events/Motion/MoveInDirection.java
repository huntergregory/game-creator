package Engine.src.Controller.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class MoveInDirection extends MotionEvent {

    public MoveInDirection(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double tempVel = motionComponent.getMovementVelocity();
        double tempXVel = tempVel * (double) args[0];
        double tempYVel = tempVel * (double) args[1];
        SetXPosition setX = new SetXPosition(myEngineInstances, myGameEngineObjects);
        SetYPosition setY = new SetYPosition(myEngineInstances, myGameEngineObjects);
        setX.activate(engineInstance, tempXVel * stepTime);
        setY.activate(engineInstance, tempYVel * stepTime);
    }
}

