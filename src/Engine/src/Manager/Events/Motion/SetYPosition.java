package Engine.src.Manager.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;

import java.util.ArrayList;
import java.util.Set;

public class SetYPosition extends MotionEvent {
    public SetYPosition(Set<EngineInstance> engineInstances, Class<? extends Component>[] componentClasses) {
        super(engineInstances, componentClasses, Double.class);
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
