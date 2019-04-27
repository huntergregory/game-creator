package Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    private Parent myRoot;

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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/GUI/Launcher.fxml"));

        this.myRoot = loader.load();

        Scene scene = new Scene(myRoot, 975, 500);

        stage.setTitle("Cracking");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
