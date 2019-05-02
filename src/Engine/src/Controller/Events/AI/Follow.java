package Engine.src.Controller.Events.AI;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class Follow extends AIEvent{

        public Follow(Map<String, EngineInstance> engineInstanceSet, Set<UnmodifiableEngineGameObject> engineObjects) {
            super(engineInstanceSet, engineObjects, String.class);
        }

        @Override
        protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
            follow(engineInstance, (String) args[0], stepTime) ;
        }
    }
    