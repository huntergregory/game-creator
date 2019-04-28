package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents;

import gamedata.Game;
import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Instance;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Game game, int numParameters) {
        super(game, numParameters, LogicComponent.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        LogicComponent logic = instance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
