package Launcher.Initial;

//import src.;

import Player.PlayerMain.GameLoop;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;

public class Launcher extends Application {
    private Stage myLauncherStage;
    private Stage myPlayerStage;
    private Stage myAuthoringStage;

    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Start the application by setting up the front end and back end through setUpScene, creating the
     * FrontendController and BackendController
     * @param stage stage
     */
    public void start (Stage stage) {
        initializePlayerStage();
        initializeAuthoringStage();
        initializeLauncherStage(stage);

        display(myLauncherStage); //FIXME display myLauncherStage and add buttons to display auth or player
    }

    private void initializeLauncherStage(Stage stage) {
        myLauncherStage = stage;
        var root = new BorderPane();
        Scene scene = new Scene(root);
        myLauncherStage.setScene(scene);

        HBox hbox = new HBox();
        hbox.getChildren().add(createButton("Authoring", myAuthoringStage));
        hbox.getChildren().add(createButton("Player", myPlayerStage));
        scene.setRoot(hbox);
    }

    private Button createButton(String text, Stage stageToShow) {
        Button button = new Button(text);
        button.setOnAction(e -> display(stageToShow));
        button.setMinWidth(500);
        button.setMinHeight(500);
        return button;
    }

    private void initializePlayerStage() {
        GameLoop player = new GameLoop();
        //myPlayerStage = player.getStage();
    }

    private void initializeAuthoringStage() {
        //RunAuth authoringEnvironment = new RunAuth();
        //myAuthoringStage =  authoringEnvironment.getStage(); //TODO get stage from authoring!!!!
    }

    private void display(Stage stage) {
        stage.show();
    }
}
