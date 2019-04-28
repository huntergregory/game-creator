package Engine.src.Manager.Events;

import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class Event {
    private static final String ERROR_MESSAGE = "Not the correct number of arguments.";

    protected Set<Instance> myInstances;
    private Class[] myParameters;
    //private String myConditionalScript;

    public Event(Set<Instance> instances, Class ... parameterTypes) {
        myInstances = instances;
        myParameters = parameterTypes;
        //myConditionalScript = "";
    }

    /**
     *
     * All subclasses of Event assume that Event does full error checking of both the number and type of args in execute.
     * @param instance
     * @param args
     */
    protected abstract void execute(Instance instance, Object ... args);

    public Class[] getNonInstanceParameters() {
        return myParameters;
    }

    public void activate(Instance instance, Object ... args) throws IllegalArgumentException {
        if (!parametersMatch(args))
            throw new IllegalArgumentException(ERROR_MESSAGE);
        execute(instance, args);
    }

    private boolean parametersMatch(Object ... args) {
        if (args.length != myParameters.length)
            return false;
        for (int k=0; k<args.length; k++) {
            if (!myParameters[k].isInstance(args[0]))
                return false;
        }
        return true;
    }
}
