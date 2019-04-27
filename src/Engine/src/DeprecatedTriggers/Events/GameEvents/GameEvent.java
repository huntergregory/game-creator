package Engine.src.DeprecatedTriggers.Events.GameEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import Engine.src.Controller.LevelManager;
import Engine.src.DeprecatedTriggers.Events.Event;

import java.util.List;

@Deprecated
public abstract class GameEvent extends Event {

    public GameEvent(){
        super();
    }

    public GameEvent(List<Conditional> conditionals){
        super(conditionals);
    }

    public abstract void activate(LevelManager gameManager);

    @Override
    public abstract Event copy();
}
