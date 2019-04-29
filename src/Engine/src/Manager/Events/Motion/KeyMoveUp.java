package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class KeyMoveUp extends MotionEvent {
    public KeyMoveUp(Set<EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        double stepTime = (double) args[0];
        double yVel = motionComponent.getMovementYVelocity();
        double yPos = basicComponent.getY();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetYPosition setY = new SetYPosition(myEngineInstances, components);
        setY.activate(engineInstance,yPos - yVel * stepTime);
    }
}
