package Engine.src.Manager.Events;

import Engine.src.ECS.Pair;
import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.ArrayList;
import java.util.List;

public class Patrol extends AIEvent{

    public Patrol(Game game){
        super(game, List.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        patrol(instance, (List) args[0], (Double) args[1]);
    }
}
