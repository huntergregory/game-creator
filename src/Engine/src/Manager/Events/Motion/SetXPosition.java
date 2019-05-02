package Engine.src.Manager.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.EngineInstance;

import java.util.ArrayList;
import java.util.Map;

public class SetXPosition extends MotionEvent {
    public SetXPosition(Map<String, EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double currentX = basicComponent.getX();
        double finalX = (double) args[0];
        double newX = (double) args[0];
        CollisionDetector collisionDetector = new CollisionDetector();
        ArrayList<EngineInstance> impassableColliders = collisionDetector.getImpassableColliders(engineInstance, myEngineInstances);
        for(EngineInstance impassable : impassableColliders){
            if ((collisionDetector.collideFromLeft(impassable, engineInstance) && newX < currentX) ||
                    (collisionDetector.collideFromLeft(engineInstance, impassable) && newX > currentX)) {
                finalX = currentX;
            }
        }
        basicComponent.setX(finalX);
    }
}
