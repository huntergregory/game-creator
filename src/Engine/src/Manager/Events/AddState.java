package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Components.StateComponent;
import gamedata.GameObjects.Instance;

public class AddState extends ComponentDependentEvent {
    public AddState(Game game) {
        super(game, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        instance.getComponent(StateComponent.class).addState((String) args[0]);
    }
}
