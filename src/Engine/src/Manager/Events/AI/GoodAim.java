package Engine.src.Manager.Events.AI;

import Engine.src.EngineData.EngineInstance;

import java.util.Set;

public class GoodAim extends AIEvent{

    public GoodAim(Set<EngineInstance> engineInstanceSet){
        super(engineInstanceSet, EngineInstance.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        goodAim(engineInstance, (EngineInstance) args[0], (Double) args[1], stepTime);
    }
}