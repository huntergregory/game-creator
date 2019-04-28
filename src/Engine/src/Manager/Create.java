package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Instance;

public class Create extends Event {
    public Create(Game game) {
        super(game);
    }

    @Override
    protected void execute(Instance instance, Object... args) {
        myGame.currentScene.instances.add(instance);
    }
}
