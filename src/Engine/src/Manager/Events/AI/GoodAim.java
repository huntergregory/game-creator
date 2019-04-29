package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class GoodAim extends AIEvent{

    public GoodAim(Set<Instance> instanceSet){
        super(instanceSet, Instance.class, Double.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        goodAim(instance, (Instance) args[0], (Double) args[1], (Double) args[2]);
    }
}