package Engine.src.Manager.Events.Motion;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.Components.MotionComponent;

import java.util.Set;

public class KeyMoveDown extends MotionEvent {
    public KeyMoveDown(Set<EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object ... args) {
        var motionComponent = engineInstance.getComponent(MotionComponent.class);
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double stepTime = (double) args[0];
        double yPos = basicComponent.getY();
        double yVel = motionComponent.getMovementYVelocity();
        Class<? extends Component>[] components = new Class[]{MotionComponent.class, BasicComponent.class};
        SetYPosition setY = new SetYPosition(myEngineInstances, components);
        setY.activate(engineInstance,yPos + yVel * stepTime);
    }
}
