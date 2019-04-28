package Engine.src.Manager.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetYPosition extends MotionEvent {
    public SetYPosition(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {\
        var basicComponent = instance.getComponent(BasicComponent.class);
        double currentY = basicComponent.getY();
        double finalY = (double) args[0];
        //Adjust this when collision detector is fixed
        Integer[] impassableColliders = collisionDetector.getImpassableColliders(instance, myEntityMap.keySet());
        CollisionDetector collisionDetector = new CollisionDetector(this);
        for(Integer impassable : impassableColliders){
            if ((collisionDetector.collideFromLeft(impassable, obj) && newX < currentX) ||
                    (collisionDetector.collideFromLeft(obj, impassable) && newX > currentX)) {
                finalY = currentY;
            }
        }
        basicComponent.setY(finalY);
    }
}
