package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class AddToHealth extends HealthModifierEvent {
    public AddToHealth(Game game) {
        super(game, Integer.class);
    }

    //TODO: This sets the health, but doesn't add to it???
    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
