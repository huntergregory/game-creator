package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Set<Instance> instances) {
        super(instances, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        LogicComponent logic = instance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
