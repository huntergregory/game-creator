package Engine.src.Manager.Events;

import Engine.src.EngineData.Components.StateComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class RemoveState extends ComponentDependentEvent {
    public RemoveState(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, StateComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        engineInstance.getComponent(StateComponent.class).removeState((String) args[0]);
    }
}
