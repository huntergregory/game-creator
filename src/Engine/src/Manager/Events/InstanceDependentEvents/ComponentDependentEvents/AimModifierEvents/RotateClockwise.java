package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents.AimModifierEvents;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class RotateClockwise extends AimModifierEvent {
    public RotateClockwise(Game game, int numParameters) {
        super(game);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        rotateAim(instance, true);
    }
}
