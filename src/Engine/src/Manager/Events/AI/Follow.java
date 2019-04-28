package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

import java.util.Set;

public class Follow extends AIEvent{

        public Follow(Set<Instance> instanceSet) {
            super(instanceSet, Instance.class, Double.class);
        }

        @Override
        protected void modifyComponents(Instance instance, Object... args) {
            follow(instance, (Instance) args[0], (Double) args[1]) ;
        }

    }

