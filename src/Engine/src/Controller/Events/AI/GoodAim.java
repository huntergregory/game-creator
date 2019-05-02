package Engine.src.Controller.Events.AI;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class GoodAim extends AIEvent{

    public GoodAim(Map<String, EngineInstance> engineInstanceSet, Set<UnmodifiableEngineGameObject> engineObjects){
        super(engineInstanceSet, engineObjects, EngineInstance.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        goodAim(engineInstance, (String) args[0], (String) args[1], (String) args[2], stepTime);
    }
}