package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class GoodAim extends AIEvent{

    public GoodAim(Game game){
        super(game, Instance.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        goodAim(instance, (Instance) args[0], (Double) args[1], (Double) args[2]);
    }
}