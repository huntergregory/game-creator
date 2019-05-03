package Engine.src.EngineData;

import Engine.src.EngineData.ComponentExceptions.BasicComponentException;
import Engine.src.EngineData.ComponentExceptions.NoComponentException;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {
    private String myID;
    public Map<Class<? extends Component>, Component> myComponents;

    ComponentContainer(String id) {
        myID = id;
        myComponents = new HashMap<>();
    }

    ComponentContainer(String id, Map<Class<? extends Component>, Component> components) {
        this(id);
        if (components != null)
            myComponents = components;
    }

    public void addComponent(Component ... components) {
        for (Component component : components) myComponents.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) throws NoComponentException {
        if (myComponents.containsKey(componentClass))
            return (T) myComponents.get(componentClass);
        throw new NoComponentException(myID);
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

    protected Map<Class<? extends Component>, Component> copyComponents() {
        Map<Class<? extends Component>, Component> components = new HashMap<>();
        for(Class clazz: myComponents.keySet()){
            components.put(clazz, myComponents.get(clazz).copy());
        }
        return components;
    }

}
