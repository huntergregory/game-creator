package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class Flee extends AIEvent{

    public Flee(Game game) {
        super(game, Instance.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        flee(instance, (Instance) args[0], (Double) args[1]) ;
    }

   }
