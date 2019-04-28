package Engine.src.Manager.Events.Motion;

import Engine.src.ECS.CollisionDetector;
import Engine.src.Manager.Events.Motion.MotionEvent;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class SetXPosition extends MotionEvent {
    public SetXPosition(Set<Instance> instances, Class<? extends Component>[] componentClasses) {
        super(instances, componentClasses, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        var basicComponent = instance.getComponent(BasicComponent.class);
        double currentX = basicComponent.getX();
        double finalX = (double) args[0];
        //Adjust this when collision detector is fixed
        CollisionDetector collisionDetector = new CollisionDetector(this);
        Integer[] impassableColliders = collisionDetector.getImpassableColliders(instance, myEntityMap.keySet());
        for(Integer impassable : impassableColliders){
            if ((collisionDetector.collideFromLeft(impassable, obj) && newX < currentX) ||
                    (collisionDetector.collideFromLeft(obj, impassable) && newX > currentX)) {
                finalX = currentX;
            }
        }
        basicComponent.setX(finalX);
    }
}
