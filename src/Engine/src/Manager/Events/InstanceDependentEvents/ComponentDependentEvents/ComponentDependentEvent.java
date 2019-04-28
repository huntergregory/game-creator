package Engine.src.Manager.Events.InstanceDependentEvents.ComponentDependentEvents;

import Engine.src.Manager.Events.InstanceDependentEvents.InstanceDependentEvent;
import gamedata.Game;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

public abstract class ComponentDependentEvent extends InstanceDependentEvent {
    private Class<? extends Component> myComponentClass;

    public ComponentDependentEvent(Game game, Class<? extends Component> componentClass, Class<?>... parameterTypes) {
        super(game, parameterTypes);
        this.myComponentClass = componentClass;
    }

    /**
     *
     * All subclasses of ComponentModifierEvent assume the specified components are in the instance
     * @param instance
     * @param args
     */
    protected abstract void modifyComponent(Instance instance, Object ... args);

    @Override
    protected void modifyInstance(Instance instance, Object ... args) {
        if (!instance.hasComponent(myComponentClass))
            return;
        modifyComponent(instance, args);
    }
}
