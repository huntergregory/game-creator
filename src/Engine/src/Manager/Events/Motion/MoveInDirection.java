package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class MoveInDirection extends MotionEvent {
    public MoveInDirection(Map<String, EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double tempVel = motionComponent.getMovementVelocity();
        double tempXVel = tempVel * (double) args[0];
        double tempYVel = tempVel * (double) args[1];
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myEngineInstances, components);
        SetYPosition setY = new SetYPosition(myEngineInstances, components);
        setX.activate(engineInstance, tempXVel * stepTime);
        setY.activate(engineInstance, tempYVel * stepTime);
    }
}

