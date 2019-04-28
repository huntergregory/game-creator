package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.StateComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class RemoveState extends ComponentDependentEvent {
    public RemoveState(Set<Instance> instances) {
        super(instances, StateComponent.class, String.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        instance.getComponent(StateComponent.class).removeState((String) args[0]);
    }
}
