package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents.AimModifierEvents;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class RotateCounterClockwise extends AimModifierEvent {
    public RotateCounterClockwise(Game game, int numParameters) {
        super(game, numParameters);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        rotateAim(instance, false);
    }
}
