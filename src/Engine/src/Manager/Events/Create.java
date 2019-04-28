package Engine.src.Manager.Events;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class Create extends Event {
    private static final int NUM_ARGS = 0;

    public Create(Game game) {
        super(game, NUM_ARGS);
    }

    @Override
    protected void execute(Instance instance, Object... args) {
        myGame.currentScene.instances.add(instance);
    }
}
