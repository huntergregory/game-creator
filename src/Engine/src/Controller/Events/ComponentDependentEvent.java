package Engine.src.Controller.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Set;

public abstract class ComponentDependentEvent extends InstanceDependentEvent {
    private Class<? extends Component>[] myComponentClasses;

    public ComponentDependentEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<? extends Component> componentClass, Class ... parameterTypes) {
        super(engineInstances, engineObjects, parameterTypes);
        myComponentClasses = new Class[] {componentClass};
    }

    public ComponentDependentEvent(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class<? extends Component>[] componentClasses, Class ... parameterTypes) {
        super(engineInstances, engineObjects, parameterTypes);
        myComponentClasses = componentClasses;
    }

    /**
     *
     * All subclasses of ComponentModifierEvent assume the specified components are in the engineInstance
     * @param engineInstance
     * @param args
     */
    protected abstract void modifyComponents(EngineInstance engineInstance, double stepTime, Object ... args);

    @Override
    protected void modifyInstance(EngineInstance engineInstance, double stepTime, Object ... args) {
        for (Class<? extends Component> componentClass : myComponentClasses) {
            if (!engineInstance.hasComponent(componentClass)) {
                return;
            }
        }
        modifyComponents(engineInstance, stepTime, args);
    }
}
