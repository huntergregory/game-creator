package Engine.src.Triggers.Events.GameEvents;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Controller.LevelManager;
import Engine.src.Triggers.Events.Event;

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
