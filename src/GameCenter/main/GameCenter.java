package GameCenter.main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * GameCenter.java is the launcher for the Game Center, where a user can launch games through the Player, launch the
 * Authoring Environment to edit games, do several actions to the game, and interact with other users through the
 * social hub.
 *
 * GameCenter.java works in conjunction with GameCenter.fxml and GUIStyle.css, both found in the resources folder, as
 * well as GameCenterController.
 *
 * GameCenter.java, GameCenter.fxml & GUIStyle.css, and GameCenterController.java are the model, view, and controller,
 * respectively. Keep this in mind when refactoring/writing new code.
 *
 * TODO: Figure out how to use custom fonts in .fxml or .css
 * TODO: Implement Social Hub
 * TODO: Add login items/user picture in conjunction with the data team
 *
 * @author Januario Carreiro
 * 14 April 2019
 */
public class GameCenter extends Application {
    private Parent myRoot;
    private GameCenterController myGCC;

    /**
     * Method to launch the application. Required when extending Application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method to set up the stage. Required when extending Application.
     *
     * @param stage window for the Game Center.
     * @throws Exception if resources could not be found, which should never happen.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/GUI/GameCenter.fxml"));

        this.myRoot = loader.load();
        this.myGCC = loader.getController();

        myRoot.getStylesheets().add(this.getClass().getResource("/GUI/GUIStyle.css").toString());
        myGCC.initGameCenter();

        Scene scene = new Scene(myRoot, 975, 500);

        stage.setTitle("Game Center");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
