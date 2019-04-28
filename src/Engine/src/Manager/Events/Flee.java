package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.LOSComponent;
import gamedata.GameObjects.Instance;

public class Flee extends AIEvent{

    public Flee(Game game) {
        super(game, Instance.class, String.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {

    }

    @Override
    protected void modifyComponents(Instance referenceInstance, Instance targetInstance, String movementType){

    }
   }
