package Engine.src.Controller.Events;

import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.StateComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class AddState extends ComponentDependentEvent {
    public AddState(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        engineInstance.getComponent(StateComponent.class).addState((String) args[0]);
    }
}
