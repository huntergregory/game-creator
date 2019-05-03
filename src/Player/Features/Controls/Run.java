package Player.Features.Controls;

import Player.PlayerMain.PlayerStage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Run extends Control {
    public Run(PlayerStage context, String gameName) {
        super(context, gameName,"Play", "Press here to play " + gameName + "!");
    }

    @Override
    protected EventHandler<MouseEvent> action() {
<<<<<<< HEAD
        return event -> myContext.load(new Game());
=======
        return event -> myContext.run(myGameName);
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc
    }
}
