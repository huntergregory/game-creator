package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Instance;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Game game) {
        super(game, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        LogicComponent logic = instance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
