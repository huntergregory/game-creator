package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class RotateClockwise extends AimModifierEvent {
    public RotateClockwise(Game game, int numParameters) {
        super(game);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        rotateAim(instance, true);
    }
}
