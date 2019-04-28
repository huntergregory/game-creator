package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class Event {
    private static final String ERROR_MESSAGE = "Not the correct number of arguments.";

    protected Game myGame;
    private int myNonInstanceParameters;
    //private String myConditionalScript;

    public Event(Game game, int numParameters) {
        myGame = game;
        myNonInstanceParameters = numParameters;
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
        return myNonInstanceParameters;
    }

    public void activate(Instance instance, Object ... args) {
        if (args.length != myNonInstanceParameters)
            throw new IllegalArgumentException(ERROR_MESSAGE);
        execute(instance, args);
        /*if (conditional) {
            execute(instance, args);
        }*/
    }

    /*public void setConditional(String script) {
        myConditionalScript = script;
    }*/
}
