package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class Event {

    protected Game myGame;
    //private String myConditionalScript;

    public Event(Game game) {
        myGame = game;
        //myConditionalScript = "";
    }

    protected abstract void execute(Instance instance, Object ... args);

    public void activate(Instance instance, Object ... args) {
        /*if (conditional) {
            execute(instance, args);
        }*/
    }

    /*public void setConditional(String script) {
        myConditionalScript = script;
    }*/
}
