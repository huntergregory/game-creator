package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class AddToHealth extends HealthModifier {
    public AddToHealth(Game game, int numParameters) {
        super(game, numParameters);
    }

    @Override
    protected void modifyComponent(Instance instance, Object ... args) {
        setHealth(instance, (int) args[0]);
    }
}
