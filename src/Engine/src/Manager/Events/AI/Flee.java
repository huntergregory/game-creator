package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class Flee extends AIEvent{

    public Flee(Set<Instance> instanceSet) {
        super(instanceSet, Instance.class, Double.class);
    }

    @Override
    protected void modifyComponents(Instance instance, Object... args) {
        flee(instance, (Instance) args[0], (Double) args[1]) ;
    }

   }
