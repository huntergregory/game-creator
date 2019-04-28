package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents;

import gamedata.Game;
import gamedata.GameObjects.Components.StateComponent;
import gamedata.GameObjects.Instance;

public class AddState extends ComponentDependentEvent {
    public AddState(Game game, int numParameters) {
        super(game, numParameters, StateComponent.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        instance.getComponent(StateComponent.class).addState((String) args[0]);
    }
}
