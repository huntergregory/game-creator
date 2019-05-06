package Engine.src.EngineData;

import Engine.src.EngineData.ComponentExceptions.BasicComponentException;
import Engine.src.EngineData.ComponentExceptions.NoComponentException;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows one to maintain components in an easily accessible way.
 * @author Hunter Gregory
 */
public abstract class ComponentContainer {
    private String myID;
    Map<Class<? extends Component>, Component> myComponents;

    /**
     * Create a ComponentContainer
     * @param id
     */
    ComponentContainer(String id) {
        myID = id;
        myComponents = new HashMap<>();
    }

    /**
     * Add any number of comma delineated components. If a component class is duplicated, then the later one will replace the former one.
     * @param components
     */
    public void addComponent(Component ... components) {
        Arrays.stream(components).forEach(component -> myComponents.put(component.getClass(), component));
    }

    /**
     * Get a given component by component class
     * @param componentClass
     * @param <T>
     * @return Component
     * @throws NoComponentException if the ComponentContainer doesn't contain the Component
     */
    public <T extends Component> T getComponent(Class<T> componentClass) throws NoComponentException {
        if (myComponents.containsKey(componentClass))
            return (T) myComponents.get(componentClass);
        throw new NoComponentException(myID);
    }

    /**
     * Remove a given component by its class
     * @param componentClass
     * @throws BasicComponentException if you try to remove the essential BasicComponent
     */
    public void removeComponent(Class<? extends Component> componentClass) throws BasicComponentException {
        if (componentClass.equals(BasicComponent.class))
            throw new BasicComponentException();
        myComponents.remove(componentClass);
    }

    /**
     * Check to prevent NoComponentExceptions when using getComponent
     * @param componentClass
     * @return true if ComponentContainer has the component
     */
    public boolean hasComponent(Class<? extends Component> componentClass) {
        return myComponents.containsKey(componentClass);
    }

    /**
     * @return String id
     */
    public String getID() {
        return myID;
    }

}
