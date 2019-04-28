package auth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunTest extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RunTest.class.getResource("/auth_components_fxml/components_dropdown.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }

    public static void main(String args[]){
        launch(args);
    }
}
