package Engine.src.Manager.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SetXPosition extends MotionEvent {

    public SetXPosition(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class);
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
