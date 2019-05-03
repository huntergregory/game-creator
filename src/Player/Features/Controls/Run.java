package Player.Features.Controls;

import Player.PlayerMain.PlayerStage;
import gamedata.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Run extends Control {


    public Run(PlayerStage context, String gameName) {
        super(context, gameName,"Play", "Press here to play " + gameName + "!");
    }

    @Override
    protected EventHandler<MouseEvent> action() {
        return event -> myContext.load(new Game());
    }
}
