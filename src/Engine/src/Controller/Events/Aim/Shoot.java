package Engine.src.Controller.Events.Aim;

import Engine.src.Controller.Events.ComponentDependentEvent;
import Engine.src.EngineData.Components.AimComponent;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.StateComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public class Shoot extends ComponentDependentEvent {
        public Shoot(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
            super(engineInstances, engineObjects, AimComponent.class, String.class);
        }

        @Override
        protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
            shoot(engineInstance, (String) args[0], engineInstance.getComponent(AimComponent.class));
        }
    }

