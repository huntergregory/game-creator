package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public abstract class Event {
    private static final String ERROR_MESSAGE = "Not the correct number of arguments.";

    protected Set<EngineInstance> myEngineInstances;
    private Class[] myParameters;
    //private String myConditionalScript;
    private final String SUBFOLDER = "";

    public Event(Set<EngineInstance> engineInstances, Class ... parameterTypes) {
        myEngineInstances = engineInstances;
        myParameters = parameterTypes;
        //myConditionalScript = "";
    }

    /**
     *
     * All subclasses of Event assume that Event does full error checking of both the number and type of args in execute.
     * @param engineInstance
     * @param args
     */
    protected abstract void execute(EngineInstance engineInstance, double stepTime, Object ... args);

    public Class[] getNonInstanceParameters() {
        return myParameters;
    }

    public void activate(EngineInstance engineInstance, double stepTime, Object ... args) throws IllegalArgumentException {
        if (!parametersMatch(args))
            throw new IllegalArgumentException(ERROR_MESSAGE);
        execute(engineInstance, stepTime, args);
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

    private String getSubfolder(){
        return SUBFOLDER;
    }
}
