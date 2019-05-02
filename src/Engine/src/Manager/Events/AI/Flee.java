package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;
import java.util.Set;

public class Flee extends AIEvent{

    public Flee(Map<String, EngineInstance> engineInstanceSet) {
        super(engineInstanceSet, EngineInstance.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        flee(engineInstance, (EngineInstance) args[0], stepTime) ;
    }

   }
