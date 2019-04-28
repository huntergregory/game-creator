package Engine.src.Manager.Events;

import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;

import java.util.Set;

public abstract class ComponentDependentEvent extends InstanceDependentEvent {
    private Class<? extends Component>[] myComponentClasses;

    public ComponentDependentEvent(Set<Instance> instances, Class<? extends Component> componentClass, Class ... parameterTypes) {
        super(instances, parameterTypes);
        myComponentClasses = new Class[] {componentClass};
    }

    public ComponentDependentEvent(Set<Instance> instances, Class<? extends Component>[] componentClasses, Class ... parameterTypes) {
        super(instances, parameterTypes);
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
