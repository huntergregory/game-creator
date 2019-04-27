package Engine.src.DeprecatedTriggers.Events.GameEvents;

import Engine.src.DeprecatedTriggers.Conditionals.Conditional;
import Engine.src.Controller.LevelManager;
import Engine.src.DeprecatedTriggers.Events.Event;

import java.util.List;

public class Victory extends GameEvent{

    public Victory(List<Conditional> conditionals){
        super(conditionals);
    }

    @Override
    public void activate(LevelManager levelManager) {
        levelManager.setLevelPass();
    }

    @Override
    public Event copy(){
        return new Victory(copyConditionals());
    }
}

