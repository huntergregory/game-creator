package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class BaseAim extends AIEvent{

    public BaseAim(Set<EngineInstance> engineInstanceSet){
        super(engineInstanceSet, EngineInstance.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, Object... args) {
        baseAim(engineInstance, (EngineInstance) args[0], (Double) args[1]);
    }
}
