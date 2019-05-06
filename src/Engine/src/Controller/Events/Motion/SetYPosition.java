package Engine.src.Controller.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to set an instance's y position based on it's MotionComponent
 * @author David Liu
 */
public class SetYPosition extends MotionEvent {

    public SetYPosition(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, new Class[]{MotionComponent.class, BasicComponent.class}, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args) {
        var basicComponent = engineInstance.getComponent(BasicComponent.class);
        double currentY = basicComponent.getY();
        double finalY = (double) args[0];
        double newY = (double) args[0];
        //Adjust this when collision detector is fixed
        CollisionDetector collisionDetector = new CollisionDetector();
        ArrayList<EngineInstance> impassableColliders = collisionDetector.getImpassableColliders(engineInstance, myEngineInstances);
        for(EngineInstance impassable : impassableColliders){
            if ((collisionDetector.collideFromTop(impassable, engineInstance) && newY < currentY) ||
                    (collisionDetector.collideFromTop(engineInstance, impassable) && newY > currentY)) {
                finalY = currentY;
            }
        }
        basicComponent.setY(finalY);
    }
}
