package Engine.src.Manager;

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
