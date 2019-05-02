package auth.screens;

import auth.helpers.ScreenHelpers;
import gamedata.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExportScreen {
    private Stage mainStage;
    public ExportScreen(Game game) {
        mainStage = new Stage();
        mainStage.setTitle("Export Game");
        mainStage.setResizable(false);
        mainStage.setWidth(500);
        mainStage.setWidth(500);
    }

    public void run() {
        try {
            Parent root = new FXMLLoader(ExportScreen.class.getResource("/auth_misc_fxml/export.fxml")).load();
            Scene scene = new Scene(root, 500, 500);
            mainStage.setScene(scene);

            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
