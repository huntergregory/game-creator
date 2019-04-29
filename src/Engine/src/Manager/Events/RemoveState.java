package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.StateComponent;

import java.util.Set;

public class RemoveState extends ComponentDependentEvent {
    public RemoveState(Set<EngineInstance> engineInstances) {
        super(engineInstances, StateComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object... args) {
        engineInstance.getComponent(StateComponent.class).removeState((String) args[0]);
    }
}
