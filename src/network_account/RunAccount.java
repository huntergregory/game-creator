package network_account;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RunAccount extends Application {
    public static void main(String[] args){
        launch(args);
    }
    public static  Font sofiaPro, sofiaProSmall, bebasKai, bebasKaiMedium;

    @Override
    public void start(Stage primaryStage){
        IdentityManager identityManager = new IdentityManager();
        identityManager.setStageLogin();
        try {
            sofiaPro = Font.loadFont(RunAccount.class.getResource("/fonts/sofiapro-light.otf").openStream(),30);
            sofiaProSmall = Font.loadFont(RunAccount.class.getResource("/fonts/sofiapro-light.otf").openStream(),15);
            bebasKai = Font.loadFont(RunAccount.class.getResource("/fonts/bebaskai.otf").openStream(),15);
            bebasKaiMedium = Font.loadFont(RunAccount.class.getResource("/fonts/bebaskai.otf").openStream(),25);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(identityManager.getIdentity().getName());
//        System.out.println("Past identity stage");
    }
}
