package gamedata.NewData;

import gamedata.Components.Component;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {
    private String myID;
    private Map<Class<? extends Component>, Component> myComponents;

    public ComponentContainer(String type, String id) {
        myID = id;
        myComponents = new HashMap<>();
    }

    public void addComponent(Component component) {
        myComponents.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) throws NoComponentException {
        if (myComponents.containsKey(componentClass))
            return (T) myComponents.get(componentClass);
        throw new NoComponentException(myID);
        /*for (Class<?> clazz : myComponents.keySet()) {
            if (clazz.equals(componentClass))
                return (T) myComponents.get(componentClass);
        }*/
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        myComponents.remove(componentClass);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return myComponents.containsKey(componentClass);
    }

    public String getID() {
        return myID;
    }
}
