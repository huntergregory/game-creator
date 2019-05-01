package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.StateComponent;

import java.util.Map;
import java.util.Set;

public class AddState extends ComponentDependentEvent {
    public AddState(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        engineInstance.getComponent(StateComponent.class).addState((String) args[0]);
    }
}
