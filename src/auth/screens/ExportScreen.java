package auth.screens;

import auth.auth_fxml_controllers.ExportController;
import gamedata.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExportScreen {
    private Stage mainStage;
    private Game game;
    public ExportScreen(Game game) {
        this.game = game;
        mainStage = new Stage();
        mainStage.setTitle("Export Game");
        mainStage.setResizable(false);
        mainStage.setWidth(500);
        mainStage.setHeight(400);
    }

    public void closeThis() {
        mainStage.close();
    }

    public void run() {
        try {
            var loader = new FXMLLoader(ExportScreen.class.getResource("/auth_misc_fxml/export.fxml"));
            Parent root = loader.load();
            loader.<ExportController>getController().initGame(this.game, this);
            Scene scene = new Scene(root, 500, 400);
            mainStage.setScene(scene);

            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
