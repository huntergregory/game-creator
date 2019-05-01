package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Patrol extends AIEvent{

    public Patrol(Map<String, EngineInstance> engineInstanceSet){
        super(engineInstanceSet, List.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        patrol(engineInstance, (List) args[0], stepTime);
    }
}
