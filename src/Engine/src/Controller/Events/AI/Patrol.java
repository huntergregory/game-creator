package Engine.src.Controller.Events.AI;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Patrol extends AIEvent{

    public Patrol(Map<String, EngineInstance> engineInstanceSet, Set<UnmodifiableEngineGameObject> engineObjects){
        super(engineInstanceSet, engineObjects, List.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        patrol(engineInstance, (List) args[0], stepTime);
    }
}
