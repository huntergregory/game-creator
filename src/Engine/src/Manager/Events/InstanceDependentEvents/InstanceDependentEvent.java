package Engine.src.Manager.Events.InstanceDependentEvents;

import Engine.src.Manager.Events.Event;
import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class InstanceDependentEvent extends Event {
    public InstanceDependentEvent(Game game, int numParameters) {
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
