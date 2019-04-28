package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents;

import gamedata.Game;
import gamedata.GameObjects.Components.ScoreComponent;
import gamedata.GameObjects.Instance;

public class IncreaseScore extends ComponentDependentEvent {
    public IncreaseScore(Game game, int numParameters) {
        super(game, numParameters, ScoreComponent.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        ScoreComponent score = instance.getComponent(ScoreComponent.class);
        score.setScore(score.getScore() + (double) args[0]);
    }
}
