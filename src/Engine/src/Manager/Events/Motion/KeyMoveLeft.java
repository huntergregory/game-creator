package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Set;

public class KeyMoveLeft extends MotionEvent {
    public KeyMoveLeft(Set<EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double xPos = basicComponent.getX();
        double stepTime = (double) args[0];
        double xVel = motionComponent.getMovementXVelocity();
        SetXPosition setX = new SetXPosition(myEngineInstances);
        setX.activate(engineInstance, xPos - xVel * stepTime);
    }

}
