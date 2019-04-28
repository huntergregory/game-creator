package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.List;
import java.util.Set;

public class Patrol extends AIEvent{

    public Patrol(Set<Instance> instanceSet){
        super(instanceSet, List.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        patrol(instance, (List) args[0], (Double) args[1]);
    }
}
