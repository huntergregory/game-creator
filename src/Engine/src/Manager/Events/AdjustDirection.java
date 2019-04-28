package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class AdjustDirection extends MotionEvent {
    public AdjustDirection(Game game) {
        super(game, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        double angle = getAngle(instance) + (double) args[0];
        adjustVelocitiesByAngle(instance, angle);
    }
}
