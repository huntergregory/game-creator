package Engine.src.EngineData.Components;

/**
 * A generic building block for game objects and instances. Must be a class instead of an interface so that the engine can use the class grabber
 * for the Component.class package to bind all components in Groovy
 *
 * @author Hunter Gregory
 * @author Daniel Kingsbury
 */
public abstract class Component {
    /**
     * @return a copy of the Component
     */
    public abstract Component copy();

}
