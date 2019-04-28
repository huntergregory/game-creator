package ez_engine;

import gamedata.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static ez_engine.RenderingHelpers.*;

public class Engine {
    private Stage mainStage;
    private Game game;

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
}
