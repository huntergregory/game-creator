package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class InstanceModifierEvent extends Event{
    public InstanceModifierEvent(Game game, int numParameters) {
        super(game, numParameters);
    }

    protected boolean sceneContainsInstance(Instance instance) {
        return myGame.currentScene.instances.contains(instance);
    }
}
