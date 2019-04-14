package Player.src.Features.Controls;

import Player.src.PlayerMain.PlayerStage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Run extends Control {
    public Run(PlayerStage context, String gameName) {
        super(context, gameName,"Play", "Press here to play " + gameName + "!");
    }

    @Override
    protected EventHandler<MouseEvent> action() {
        return event -> myContext.run(myGameName);
    }
}
