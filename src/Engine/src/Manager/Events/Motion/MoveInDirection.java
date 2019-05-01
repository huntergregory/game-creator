package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Map;
import java.util.Set;

public class MoveInDirection extends MotionEvent {

    public MoveInDirection(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double tempVel = motionComponent.getMovementVelocity();
        double tempXVel = tempVel * (double) args[0];
        double tempYVel = tempVel * (double) args[1];
        SetXPosition setX = new SetXPosition(myEngineInstances);
        SetYPosition setY = new SetYPosition(myEngineInstances);
        setX.activate(engineInstance, tempXVel * stepTime);
        setY.activate(engineInstance, tempYVel * stepTime);
    }
}

