package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.LogicComponent;

import java.util.Set;

public class AddLogic extends ComponentDependentEvent {
    public AddLogic(Set<EngineInstance> engineInstances) {
        super(engineInstances, LogicComponent.class, String.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        LogicComponent logic = engineInstance.getComponent(LogicComponent.class);
        logic.setLogic(logic.getLogic() + args[0]);
    }
}
