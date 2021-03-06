package dummy_player;

import ez_engine.Engine;
import gamedata.Game;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

import static auth.Dimensions.CANVAS_HEIGHT;
import static auth.Dimensions.CANVAS_WIDTH;
import static dummy_player.PlayerHelpers.getGameFromFile;

public class Player {
    private Game game;
    private boolean debug = false;
    private Stage mainStage;
    private Engine gameEngine;
    private GroovyShell groovyEngine;
    private Binding sharedData;

    public Player(String title) {
        mainStage = new Stage();
        mainStage.setResizable(false);
        mainStage.setTitle(title);
        mainStage.setWidth(CANVAS_WIDTH);
        mainStage.setHeight(CANVAS_HEIGHT);

        gameEngine = new Engine(mainStage);
        sharedData = new Binding();
        sharedData.setProperty("Engine", gameEngine);
        sharedData.setProperty("ENV_WIDTH", CANVAS_WIDTH);
        sharedData.setProperty("ENV_HEIGHT", CANVAS_HEIGHT);
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

    private int waitToLoad = 50; // How many cycles
    private void step (double elapsedTime) {
        if (waitToLoad-- < 0) {
            String sceneScript = game.scenes.get(gameEngine.getCurrentScene()).sceneLogic;
            sharedData.setProperty("elapsedTime", elapsedTime);
            groovyEngine.evaluate(sceneScript);
        } else {
            // TODO Show a spinner
        }
    }
}
