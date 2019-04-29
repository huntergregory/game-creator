package Player.Features.Controls;

import Player.PlayerMain.PlayerStage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Rate extends Control {
    /**
     * Creates a button instance
     */
    public Rate(PlayerStage context, String gameName) {
        super(context, gameName, "Rate", "Press here to change rating of " + gameName);
    }

    @Override
    protected EventHandler<MouseEvent> action() {
        return event -> myContext.rate(myGameName);
    }
}
