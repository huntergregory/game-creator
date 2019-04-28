package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

public abstract class ComponentModifierEvent extends InstanceModifierEvent {
    private Class<? extends Component>[] myComponentClasses;

    public ComponentModifierEvent(Game game, int numParameters, Class<? extends Component> ... componentClasses) {
        super(game, numParameters);
        myComponentClasses = componentClasses;
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
        for (Class<? extends Component> componentClass : myComponentClasses) {
            if (!instance.hasComponent(componentClass))
                return;
        }
        modifyComponent(instance, args);
    }
}
