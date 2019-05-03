package Engine.src.Controller.Events.AI;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class Flee extends AIEvent{

    public Flee(Map<String, EngineInstance> engineInstanceSet, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstanceSet, engineObjects, EngineInstance.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        flee(engineInstance, (String) args[0], stepTime) ;
    }

   }
