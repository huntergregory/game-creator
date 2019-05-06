package Engine.src.EngineData.ComponentExceptions;

/**
 * A general exception caused by a problem with components with a given message.
 * @author Hunter Gregory
 */
public class ComponentException extends RuntimeException {
    public ComponentException(String message) {
        super(message);
    }
}
