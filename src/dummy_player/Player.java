package dummy_player;

import ez_engine.Engine;
import gamedata.Game;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static auth.Dimensions.*;
import static dummy_player.PlayerHelpers.*;

public class Player {
    private Game game;
    private boolean debug = false;
    private Stage mainStage;
    private Engine gameEngine;
    private GroovyShell groovyEngine;

    public Player(String title) {
        mainStage = new Stage();
        mainStage.setResizable(false);
        mainStage.setTitle(title);
        mainStage.setWidth(CANVAS_WIDTH);
        mainStage.setHeight(CANVAS_HEIGHT);

        gameEngine = new Engine(mainStage);
        var sharedData = new Binding();
        sharedData.setProperty("engine", gameEngine);
        groovyEngine = new GroovyShell(sharedData);
    }

    public void run(String path) throws FileNotFoundException {
        game = getGameFromFile(path);
        setupStage();
    }

    public void run(Game game, boolean debug) {
        this.game = game; this.debug = debug;
        setupStage();
    }

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


    private void setupStage() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        gameEngine.setGame(game);
        gameEngine.loadScene(0); // Always load scene 0 to start

        mainStage.show();
    }

    private void step (double elapsedTime) {
        String sceneScript = game.scenes.get(gameEngine.getCurrentScene()).sceneLogic;
        groovyEngine.evaluate(sceneScript);
    }
}
