package auth;


import auth.screens.CanvasScreen;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import network_account.RunAccount;
import network_account.UserIdentity;

import java.util.UUID;

/**
 *
 */
public class RunAuth extends Application {
    public static  Font sofiaPro, sofiaProSmall, bebasKai, bebasKaiMedium;


    private Stage mainStage;

    UserIdentity userIdentity;

    public RunAuth(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
        mainStage = new CanvasScreen(userIdentity).createScreen(new Stage(), this);
        mainStage.setResizable(false);
        try {
            sofiaPro = Font.loadFont(RunAccount.class.getResource("/fonts/sofiapro-light.otf").openStream(), 30);
            sofiaProSmall = Font.loadFont(RunAccount.class.getResource("/fonts/sofiapro-light.otf").openStream(), 15);
            bebasKai = Font.loadFont(RunAccount.class.getResource("/fonts/bebaskai.otf").openStream(), 15);
            bebasKaiMedium = Font.loadFont(RunAccount.class.getResource("/fonts/bebaskai.otf").openStream(), 25);
        } catch (Exception e) {}
    }

    public Stage getStage() {
        return mainStage;
    }

    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
<<<<<<< HEAD
        if (mainStage != null) {
            mainStage.close();
        }

        mainStage = new CanvasScreen(userIdentity).createScreen(stage, this);
=======
        mainStage = new CanvasScreen().createScreen(stage, this);
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc

        stage.show();
        stage.setResizable(false);
    }
    /**
     * Main function to serve as entry point
     * @param args: cmd line args
     */
    public static void main(String args[]) {
        // load custom font
        try {
            sofiaPro = Font.loadFont(RunAuth.class.getResource("/fonts/sofiapro-light.otf").openStream(),30);
            sofiaProSmall = Font.loadFont(RunAuth.class.getResource("/fonts/sofiapro-light.otf").openStream(),15);
            bebasKai = Font.loadFont(RunAuth.class.getResource("/fonts/bebaskai.otf").openStream(),15);
            bebasKaiMedium = Font.loadFont(RunAuth.class.getResource("/fonts/bebaskai.otf").openStream(),25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}