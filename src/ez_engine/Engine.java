package ez_engine;

import gamedata.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static ez_engine.RenderingHelpers.*;

public class Engine {
    private Stage mainStage;
    private Game game;
    private int currentScene = 0;

    public int getCurrentScene() {
        return currentScene;
    }

    public Engine (Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setGame(Game game) {
        this.game = game;
        RenderingHelpers.initGame(game);
    }

    public void loadScene(int sceneIndex) {
        if (game == null) throw new RuntimeException("Need to setGame() before using engine") ;
        mainStage.setScene(renderScene(game.scenes.get(sceneIndex)));
    }

    public void loadSceneById(String id) {
        int index = 0;
        for (var scene : game.scenes) {
            if (scene.sceneID.equals(id)) {
                loadScene(index);
                return;
            }
            index++;
        }
    }
}
