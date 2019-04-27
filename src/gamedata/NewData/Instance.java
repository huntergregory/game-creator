package gamedata.NewData;

import gamedata.Components.Component;

import java.util.HashMap;
import java.util.Map;

public class Instance {
    private String myType;
    private String myID;
    private Map<Class<? extends Component>, Component> myComponents;

    public Instance(String type, String id) {
        myType = type;
        myID = id;
        myComponents = new HashMap<>();
    }

    public void addComponent(Component component) {
        myComponents.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(int entityID, Class<T> desiredComponentClass) throws NoComponentException {
        for (Class<?> componentClass : myComponents.keySet()) {
            if (componentClass.equals(desiredComponentClass))
                return (T) myComponents.get(componentClass);
        }
        throw new NoComponentException(myID);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return myComponents.containsKey(componentClass);
    }

    public String getID() {
        return myID;
    }

    public String getType() {
        return myType;
    }
}
