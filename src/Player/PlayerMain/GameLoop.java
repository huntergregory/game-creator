package Player.PlayerMain;

import javafx.stage.Stage;

public class GameLoop {

    public Stage getStage() {
        PlayerStage player = new PlayerStage();
        return player.makeStage();
    }

    // TODO: Add methods for playing game

}