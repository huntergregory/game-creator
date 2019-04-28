package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class SetDirection extends MotionEvent {
    public SetDirection(Game game) {
        super(game, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        double angle = (double) args[0];
        adjustVelocitiesByAngle(instance, angle);
    }
}
