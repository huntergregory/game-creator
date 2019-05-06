package Engine.src.EngineData;

import Engine.src.EngineData.Components.Component;

import java.util.Map;

/**
 * A Decorator for a Component Container, initialized with given components and an object type.
 * @author Hunter Gregory
 */
public class EngineInstance extends ComponentContainer {
    private String myType;

    /**
     * Create an EngineInstance
     * @param id
     * @param type
     * @param components
     */
    public EngineInstance(String id, String type, Map<Class<? extends Component>, Component> components) {
        super(id);
        myType = type;
        if (components != null)
            myComponents = components;
    }

    /**
     * @return id of parent game object
     */
    public String getType() {
        return myType;
    }
}
