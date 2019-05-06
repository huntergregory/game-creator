package Engine.src.EngineData;

import Engine.src.Controller.NoInstanceException;
import Engine.src.EngineData.Components.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A conceptual object with components. Has the ability to create a concrete EngineInstance from its own components.
 * @author Hunter Gregory
 */
public class EngineGameObject extends ComponentContainer implements Cloneable {

    /**
     * Create an EngineGameObject
     * @param id
     */
    public EngineGameObject(String id) {
        super(id);
    }

    /**
     * Create an Instance from this class' current components
     * @param id
     * @return
     * @throws NoInstanceException if
     */
    public EngineInstance createInstance(String id) throws NoInstanceException {
        return new EngineInstance(id, getID(), copyComponents());
    }

    private Map<Class<? extends Component>, Component> copyComponents() {
        Map<Class<? extends Component>, Component> components = new HashMap<>();
        for(Class clazz : myComponents.keySet())
            components.put(clazz, myComponents.get(clazz).copy());
        return components;
    }
}
