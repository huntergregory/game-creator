package Player.Features;

import Player.Features.Sliders.LivesSlider;
import Player.PlayerMain.PlayerStage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is used to create buttons for pausing, resuming, restarting, and saving the game. This class also has sliders
 * for altering the time and number of lives for a game, although these were not fully integrated with other components
 * @author Carter Gay
 */
public class PlayerButtons {

    private PlayerStage myPlayerStage;
    private Button myPauseButton;
    private Button myResumeButton;
    private Button myRestartButton;
    private Button mySaveButton;
    private LivesSlider myLivesSlider;
    //private TimeSlider myTimeSlider;
    private int gamePaused = 0;

    private VBox myVBox;
    private HBox myHBox;
    private static final double BUTTON_SIZE = 50.0;
    private static final double INTER_VALUES_SPACING = 2;
    private static final String PAUSE = "PAUSE";
    private static final String RESUME = "RESUME";
    private static final String RESTART = "RESTART";
    private static final String SAVE = "SAVE";

    /**
     * Constructor for the PlayerButtons object
     * @param stage
     */
    public PlayerButtons(PlayerStage stage) {
        myPlayerStage = stage;
        createVBox();
        addGameButtons();
        addLivesSlider();
    }

    private void createVBox() {
        myVBox = new VBox();
        myVBox.setSpacing(INTER_VALUES_SPACING);
    }

    private void addGameButtons() {
        myPauseButton = createButton(PAUSE);
        myResumeButton = createButton(RESUME);
        myRestartButton = createButton(RESTART);
        mySaveButton = createButton(SAVE);
        setButtonActions();
        createHBox();
        myVBox.getChildren().add(myHBox);
    }

    private Button createButton(String title) {
        Button myButton = new Button(title);
        myButton.setPrefHeight(BUTTON_SIZE);
        return myButton;
    }

    private void setButtonActions() {
        myPauseButton.setOnAction(e -> pauseGame());
        myResumeButton.setOnAction(e -> resumeGame());
        myRestartButton.setOnAction(e -> restartGame());
        mySaveButton.setOnAction(e -> saveGame());
    }

    private void createHBox() {
        myHBox = new HBox();
        myHBox.getChildren().add(myPauseButton);
        myHBox.getChildren().add(myResumeButton);
        myHBox.getChildren().add(myRestartButton);
        myHBox.getChildren().add(mySaveButton);
    }

    private void addLivesSlider() {
        Label label = new Label("Select Lives");
        HBox myHBox = new HBox();
        myHBox.getChildren().add(label);
        myLivesSlider = new LivesSlider(myPlayerStage);
        myHBox.getChildren().add(myLivesSlider.getMainComponent());
        myVBox.getChildren().add(myHBox);
    }

//    private void addTimeSlider() {
//        Label label = new Label("Select Time");
//        HBox myHBox = new HBox();
//        myHBox.getChildren().add(label);
//        myTimeSlider = new TimeSlider(myPlayerStage);
//        myHBox.getChildren().add(myTimeSlider.getMainComponent());
//        myVBox.getChildren().add(myHBox);
//    }

    private void pauseGame() {
        gamePaused = 1;
    }

    private void resumeGame() {
        gamePaused = 0;
    }

    /**
     * Return whether the game is paused or active
     * @return
     */
    public int getGamePaused() {
        return gamePaused;
    }

    private void restartGame() {
        myPlayerStage.restartGame();
    }

    private void saveGame() {
        myPlayerStage.saveGame();
    }

    /**
     * Getter for the VBox holding the various PlayerButtons
     * @return
     */
    public Node getNode() {
        return myVBox;
    }
}
