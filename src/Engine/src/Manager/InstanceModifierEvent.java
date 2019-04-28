package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class InstanceModifierEvent extends Event{
    public InstanceModifierEvent(Game game, int numParameters) {
        super(game, numParameters);
    }

    protected abstract void modifyInstance(Instance instance, Object ... args);

    @Override
    protected void execute(Instance instance, Object... args) {
        if (myGame.currentScene.instances.contains(instance))
            return;
        modifyInstance(instance, args);
    }
}
