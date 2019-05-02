package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class Follow extends AIEvent{

        public Follow(Map<String, EngineInstance> engineInstanceSet) {
            super(engineInstanceSet, EngineInstance.class, Double.class);
        }

        @Override
        protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
            follow(engineInstance, (EngineInstance) args[0], stepTime) ;
        }

    }

