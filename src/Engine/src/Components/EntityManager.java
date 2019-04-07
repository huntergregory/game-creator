package Components;

import java.util.HashMap;
import java.util.List;

public class EntityManager {
    private HashMap<Integer, List<Component>> myEntityMap;
    private int myFramesPerSecond;

    public EntityManager(int framesPerSecond) {
        myFramesPerSecond = framesPerSecond;
        myEntityMap = new HashMap<>();
    }

    public void kill(int entityID) {
        myEntityMap.remove(entityID);
    }

    public void create(int entityID, List<Component> components) {
        //TODO error checking
        myEntityMap.put(entityID, components);
    }

    public void move(int entityID) {
        try {
            var motionComponent = getComponent(entityID, MotionComponent.class);
            var positionComponent = getComponent(entityID, PositionComponent.class);
            //TODO update position based on velocity, velocity based on accel
        }
        catch (NoEntityException | NoComponentException e) {
            return;
        }
    }

    //can return null
    private Component getComponent(int entityID, Class<? extends Component> componentClass) throws NoEntityException, NoComponentException {
        var components = getAllComponents(entityID);
        for (Component component : components) {
            if (component instanceof componentClass)
                return component;
        }
        return null;
    }

    private List<Component> getAllComponents(int entityID) throws NoEntityException {
        List<Component> components = myEntityMap.get(entityID);
        if (components == null)
            throw new NoEntityException();
        return components;
    }
}
