package Engine.src.Manager.Events;

import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        LogicComponent logic = engineInstance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
