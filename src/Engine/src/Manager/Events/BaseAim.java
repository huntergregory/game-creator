package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class BaseAim extends AIEvent{

    public BaseAim(Game game){
        super(game, Instance.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        baseAim(instance, (Instance) args[0], (Double) args[1]);
    }
}
