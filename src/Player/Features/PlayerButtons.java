package Player.Features;

import Player.Features.Sliders.LivesSlider;
import Player.Features.Sliders.TimeSlider;
import Player.PlayerMain.PlayerStage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerButtons {

    private PlayerStage myPlayerStage;
    private Button myPauseButton;
    private Button myResumeButton;
    private Button myRestartButton;
    private Button mySaveButton;
    private LivesSlider myLivesSlider;
    private TimeSlider myTimeSlider;
    private int gamePaused = 0;

    private VBox myVBox;
    private static final double INTER_VALUES_SPACING = 2;

    public PlayerButtons(PlayerStage stage) {
        myPlayerStage = stage;
        createVBox();
        addGameButtons();
        addGameSliders();
    }


    private void createVBox() {
        myVBox = new VBox();
        myVBox.setSpacing(INTER_VALUES_SPACING);
    }

    private void addGameButtons() {
        HBox myHBox = new HBox();
        myPauseButton = new Button("PAUSE");
        myPauseButton.setOnAction(e -> pauseGame());
        myResumeButton = new Button("RESUME");
        myResumeButton.setOnAction(e -> resumeGame());
        myRestartButton = new Button("RESTART");
        myRestartButton.setOnAction(e -> restartGame());
        mySaveButton = new Button("SAVE");
        mySaveButton.setOnAction(e -> saveGame());
        myHBox.getChildren().add(myPauseButton);
        myHBox.getChildren().add(myResumeButton);
        myHBox.getChildren().add(myRestartButton);
        myHBox.getChildren().add(mySaveButton);
        myVBox.getChildren().add(myHBox);
    }

    private void addGameSliders() {
        addLivesSlider();
        addTimeSlider();
    }

    private void addLivesSlider() {
        Label label = new Label("Select Lives");
        HBox myHBox = new HBox();
        myHBox.getChildren().add(label);
        myLivesSlider = new LivesSlider(myPlayerStage);
        myHBox.getChildren().add(myLivesSlider.getMainComponent());
        myVBox.getChildren().add(myHBox);
    }

    private void addTimeSlider() {
        Label label = new Label("Select Time");
        HBox myHBox = new HBox();
        myHBox.getChildren().add(label);
        myTimeSlider = new TimeSlider(myPlayerStage);
        myHBox.getChildren().add(myTimeSlider.getMainComponent());
        myVBox.getChildren().add(myHBox);
    }

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

    public Node getNode() {
        return myVBox;
    }
}
