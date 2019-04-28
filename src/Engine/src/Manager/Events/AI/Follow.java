package Engine.src.Manager.Events.AI;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class Follow extends AIEvent{

        public Follow(Game game) {
            super(game, Instance.class, Double.class);
        }

        @Override
        protected void modifyComponents(Instance instance, Object... args) {
            follow(instance, (Instance) args[0], (Double) args[1]) ;
        }

    }

