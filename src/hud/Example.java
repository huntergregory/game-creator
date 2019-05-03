package hud;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A template for an example application that runs a game with a Heads Up Display with built-in, GUI controllable plotting features.
 *
 * @author Hunter Gregory
 */
public class Example extends Application {
    private static final String STYLESHEET = "hud.css";
    private static final Paint BACKGROUND = Color.BLACK;
    private static final double HUD_WIDTH = 400;
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;
    private static final boolean INCLUDE_PLOTTER = true;

    private static final int FRAMES_PER_SECOND = 15;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int HUD_UPDATE_DELAY = 10;


    private BorderPane myBorderPane;
    private HUDView myHud;
    //add private instance variable of engine
    private Pane myGameRoot;

    private DataTracker<Double> myXPosTracker;
    private DataTracker<Double> myYPosTracker;
    private DataTracker<Double> myTimeTracker;
    private DataTracker<Integer> myLivesTracker;
    private DataTracker<String> myPowerupTracker;
    //etc...
    private int myGameLoopCount;

    @Override
    public void start(Stage stage) {
        //initialize game engine

        initDataTrackers();
        initBorderPane();

        //add all image views to myGameRoot

        Scene gameScene = new Scene(myBorderPane, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
        gameScene.getStylesheets().add(STYLESHEET);
        stage.setScene(gameScene);
        stage.show();

        gameScene.setOnKeyPressed(e -> {}); //add key events if necessary

        animate();
    }

    private void setHud() {
        myHud = new HUDView(HUD_WIDTH, SCREEN_HEIGHT, getHudTitle(), INCLUDE_PLOTTER, myXPosTracker,
                                                                                      myYPosTracker,
                                                                                      myTimeTracker,
                                                                                      myLivesTracker,
                                                                                      myPowerupTracker);
    }

    private void initBorderPane() {
        myBorderPane = new BorderPane();
        myGameRoot = new Pane();
        myBorderPane.setCenter(myGameRoot);
        setHud();
        myBorderPane.setLeft(myHud.getNode());
    }

    private void animate() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        //iterate once through the game loop

        if (myGameLoopCount % HUD_UPDATE_DELAY == 0) {
            updateDataTrackers();
            myHud.update();
        }

        myGameLoopCount++;
    }

    //TODO include your own trackers
    private void initDataTrackers() {
        myXPosTracker = new DataTracker<>("X Position");
        myYPosTracker = new DataTracker<>("Y Position");
        myTimeTracker = new DataTracker<>("Time");
        myLivesTracker = new DataTracker<>("Lives");
        myPowerupTracker = new DataTracker<>("Powerup");
    }

    //TODO include your own trackers and update data trackers with real values
    private void updateDataTrackers() {
        myTimeTracker.storeData(myGameLoopCount * 1.0); //TODO convert to actual time with millisecond delay/fps
        myXPosTracker.storeData(getXPosition());
        myYPosTracker.storeData(getYPosition());
        myLivesTracker.storeData(getNumLives());
        myPowerupTracker.storeData(getCurrentPowerup());
        //...store other data
    }

    //low level, example data getters
    private String getHudTitle() { return "Level " + getCurrentLevelNumber(); }
    private int getCurrentLevelNumber() { return 1; }
    private double getXPosition() {return 0;}
    private double getYPosition() {return 0;}
    private int getNumLives() { return 3; }
    private String getCurrentPowerup() { return "Lasers"; }
}
