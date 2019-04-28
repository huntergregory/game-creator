package Engine.src.Manager.Events.Health;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class SetHealth extends HealthModifierEvent {
    public SetHealth(Game game) {
        super(game, Integer.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
