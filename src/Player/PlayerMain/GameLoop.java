package Player.PlayerMain;

import gamecenter.main.GameCenterController;
import javafx.stage.Stage;

public class GameLoop {

    public Stage getStage() {
        PlayerStage player = new PlayerStage(new GameCenterController());
        return player.makeStage();
    }

    // TODO: Add methods for playing game

}