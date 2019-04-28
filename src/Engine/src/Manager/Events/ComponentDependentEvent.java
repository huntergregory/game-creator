package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentDependentEvent extends InstanceDependentEvent {
    private List<Class<? extends Component>> myComponentClasses;

    public ComponentDependentEvent(Game game, Class<? extends Component> componentClass, Class<?>... parameterTypes) {
        super(game, parameterTypes);
        myComponentClasses = new ArrayList<>();
        myComponentClasses.add(componentClass);
    }

    public ComponentDependentEvent(Game game, List<Class<? extends Component>> componentClasses, Class<?>... parameterTypes) {
        super(game, parameterTypes);
        this.myComponentClasses = componentClasses;
    }

    /**
     *
     * All subclasses of ComponentModifierEvent assume the specified components are in the instance
     * @param instance
     * @param args
     */
    protected abstract void modifyComponents(Instance instance, Object ... args);

    @Override
    protected void modifyInstance(Instance instance, Object ... args) {
        for(Class<? extends Component> myComponentClass : myComponentClasses) {
            if (!instance.hasComponent(myComponentClass)) return;
        }
        modifyComponents(instance, args);
    }
}
