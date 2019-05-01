package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Set;

public class KeyMoveRight extends MotionEvent {
    public KeyMoveRight(Set<EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double xVel = motionComponent.getMovementXVelocity();
        double xPos = basicComponent.getX();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetXPosition setX = new SetXPosition(myEngineInstances, components);
        setX.activate(engineInstance, xPos - xVel * stepTime);
    }
}
