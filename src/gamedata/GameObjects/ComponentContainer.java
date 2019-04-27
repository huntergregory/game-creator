package gamedata.GameObjects;

import gamedata.GameObjects.ComponentExceptions.BasicComponentException;
import gamedata.GameObjects.ComponentExceptions.NoComponentException;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {
    private String myID;
    private Map<Class<? extends Component>, Component> myComponents;

    ComponentContainer(String id) {
        myID = id;
        myComponents = new HashMap<>();
        addComponent(new BasicComponent("", 0,0,0,0)); //FIXME add default properties file
    }

    ComponentContainer(String id, Map<Class<? extends Component>, Component> components) {
        this(id);
        if (components != null && components.containsKey(BasicComponent.class))
            myComponents = components;
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

    public <T extends Component> void removeComponent(Class<T> componentClass) throws BasicComponentException {
        if (componentClass.equals(BasicComponent.class))
            throw new BasicComponentException();
        myComponents.remove(componentClass);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return myComponents.containsKey(componentClass);
    }

    public String getID() {
        return myID;
    }

    protected Map<Class<? extends Component>, Component> getComponents() {
        return myComponents;
    }
}
