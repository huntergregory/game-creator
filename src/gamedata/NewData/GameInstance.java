package gamedata.NewData;

import NoEntityException;
import NoComponentException;
import gamedata.Components.Component;

import java.util.HashMap;
import java.util.Map;

public class GameInstance {
    private String myID;
    private Map<Class<? extends Component>, Component> myComponents;

    public GameInstance(String id) {
        myID = id;
        myComponents = new HashMap<>();
    }

    public String getID() {
        return myID;
    }

    public <T extends Component> T getComponent(Class<? extends Component> componentClass) {

    }

    public void addComponent(int entityID, Component component) {
        try {
            var components = getAllComponents(entityID);
            components.put(component.getClass(), component);
            myEntityMap.put(entityID, components);
        }
        catch (NoEntityException e) {
            System.out.println("Entity " + entityID + " does not exist");
        }
    }

    //can return null
    public <T extends Component> T getComponent(int entityID, Class<T> componentClass) throws NoEntityException, NoComponentException {
        try {
            var components = getAllComponents(entityID);
            for (Class<?> componentName: components.keySet()) {
                if (componentName.equals(componentClass)) {
                    return (T) components.get(componentName);
                }
            }
            return null;
        }
        catch(NoEntityException e) {
            System.out.println("Entity " + entityID + " does not exist");
            return null;
        }
    }

    private boolean hasComponent(int entityID, Class<?> componentClass) {
        return myEntityMap.containsKey(componentClass);
    }

    private Map<Class<? extends Component>, Component> getAllComponents(int entityID) throws NoEntityException {
        Map<Class<? extends Component>, Component> components = myEntityMap.get(entityID);
        if (components == null)
            throw new NoEntityException("Entity " + entityID + " does not exist");
        return components;
    }
}
