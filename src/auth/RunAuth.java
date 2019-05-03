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
        if (mainStage != null) {
            mainStage.close();
        }

        mainStage = new CanvasScreen(userIdentity).createScreen(stage, this);

        stage.show();
        stage.setResizable(false);
    }
}