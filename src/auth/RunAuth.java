package auth;


import auth.screens.CanvasScreen;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 */
public class RunAuth extends Application {
    public static  Font sofiaPro, sofiaProSmall, bebasKai, bebasKaiMedium;


    private Stage mainStage;

    public RunAuth() {
        mainStage = new CanvasScreen().createScreen(new Stage(), this);
        //mainStage.setResizable(false);
    }

    public Stage getStage() {
        return mainStage;
    }

    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        if (mainStage != null) {
            mainStage.close();
        }
        mainStage = new CanvasScreen().createScreen(stage, this);

        stage.show();
        stage.setResizable(false);
    }
}