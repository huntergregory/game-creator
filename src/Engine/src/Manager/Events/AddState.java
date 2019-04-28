package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Components.StateComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class AddState extends ComponentDependentEvent {
    public AddState(Set<Instance> instances) {
        super(instances, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        instance.getComponent(StateComponent.class).addState((String) args[0]);
    }
}
