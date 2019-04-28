package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Components.AimComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Components.MotionComponent;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class ExampleEvent extends ComponentDependentEvent {
    //you can't create a generic array, so when you make an array, it needs to be Class[], not Class<? extends Component>
    private static final Class<? extends Component>[] COMPONENTS = new Class[]{MotionComponent.class, AimComponent.class};

    public ExampleEvent(Set<Instance> instances) {
        super(instances, LogicComponent.class, String.class, Double.class); //if you'll be working with ONE component
                      // ^ component class      ^ parameter classes ...
        //just an example, this event wouldn't have the Double.class parameter though (see modifyComponent method)


        //OR with multiple components...
        super(instances, COMPONENTS, String.class, Double.class);
    }

    @Override
    protected void modifyComponent(Instance instance, Object... args) {
        instance.getComponent(LogicComponent.class).setLogic((String) args[0]);
    }
}
