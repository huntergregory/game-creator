package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class Follow extends AIEvent{

        public Follow(Set<EngineInstance> engineInstanceSet) {
            super(engineInstanceSet, EngineInstance.class, Double.class);
        }

        @Override
        protected void modifyComponents(EngineInstance engineInstance, Object... args) {
            follow(engineInstance, (EngineInstance) args[0], (Double) args[1]) ;
        }

    }

