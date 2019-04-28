package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.ScoreComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class IncreaseScore extends ComponentDependentEvent {
    public IncreaseScore(Set<Instance> instances) {
        super(instances, ScoreComponent.class, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        ScoreComponent score = instance.getComponent(ScoreComponent.class);
        score.setScore(score.getScore() + (double) args[0]);
    }
}
