package Engine.src.Controller.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        LogicComponent logic = engineInstance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
