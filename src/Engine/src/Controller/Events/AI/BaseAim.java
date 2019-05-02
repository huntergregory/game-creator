package Engine.src.Controller.Events.AI;

import Engine.src.EngineData.EngineGameObject;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class BaseAim extends AIEvent{

    public BaseAim(Map<String, EngineInstance> engineInstanceSet){
        super(engineInstanceSet, EngineInstance.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        baseAim(engineInstance, (EngineInstance) args[0], (String) args[1], (Double) args[2]);
    }
}
