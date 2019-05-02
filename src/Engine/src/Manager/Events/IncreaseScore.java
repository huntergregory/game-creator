package Engine.src.Manager.Events;

import Engine.src.EngineData.Components.ScoreComponent;
import Engine.src.EngineData.EngineInstance;

import java.util.Map;

public class IncreaseScore extends ComponentDependentEvent {
    public IncreaseScore(Map<String, EngineInstance> engineInstances) {
        super(engineInstances, ScoreComponent.class, Double.class);
    }

    @Override
    protected void modifyComponents(EngineInstance engineInstance, double stepTime, Object... args) {
        ScoreComponent score = engineInstance.getComponent(ScoreComponent.class);
        score.setScore(score.getScore() + (double) args[0]);
    }
}
