package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.HashSet;

public abstract class Event {
    private static final String ERROR_MESSAGE = "Not the correct number of arguments.";

    protected Game myGame;
    protected HashSet<Instance> myInstances;
    private Class<?>[] myParameters;
    //private String myConditionalScript;

    public Event(Game game, Class<?> ... parameterTypes) {
        myGame = game;
        myParameters = parameterTypes;
        myInstances = myGame.currentScene.instances;
        //myConditionalScript = "";
    }

    /**
     *
     * All subclasses of Event assume that Event does full error checking of both the number and type of args in execute.
     * @param instance
     * @param args
     */
    protected abstract void execute(Instance instance, Object ... args);

    public int getNumberOfNonInstanceParameters() {
        return myParameters.length;
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
