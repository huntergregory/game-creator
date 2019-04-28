package Engine.src.Manager.Events;

import Engine.src.Manager.Events.Event;
import gamedata.Game;
import gamedata.GameObjects.Instance;

public abstract class InstanceDependentEvent extends Event {
    public InstanceDependentEvent(Game game, Class<?>... parameterTypes) {
        super(game, parameterTypes);
    }

    protected abstract void modifyInstance(Instance instance, Object ... args);

    @Override
    protected void execute(Instance instance, Object... args) {
        if (!myInstances.contains(instance))
            return;
        modifyInstance(instance, args);
    }
}
