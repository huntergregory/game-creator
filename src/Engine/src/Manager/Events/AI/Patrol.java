package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.List;
import java.util.Set;

public class Patrol extends AIEvent{

    public Patrol(Set<EngineInstance> engineInstanceSet){
        super(engineInstanceSet, List.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object... args) {
        patrol(engineInstance, (List) args[0], (Double) args[1]);
    }
}
