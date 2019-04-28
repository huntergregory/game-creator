package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents;

import gamedata.Game;
import gamedata.GameObjects.Components.StateComponent;
import gamedata.GameObjects.Instance;

public class RemoveState extends ComponentDependentEvent {
    public RemoveState(Game game) {
        super(game, StateComponent.class, String.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        instance.getComponent(StateComponent.class).removeState((String) args[0]);
    }
}
