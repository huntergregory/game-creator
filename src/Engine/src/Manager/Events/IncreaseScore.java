package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.ScoreComponent;
import gamedata.GameObjects.Instance;

public class IncreaseScore extends ComponentDependentEvent {
    public IncreaseScore(Game game) {
        super(game, ScoreComponent.class, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        ScoreComponent score = instance.getComponent(ScoreComponent.class);
        score.setScore(score.getScore() + (double) args[0]);
    }
}
