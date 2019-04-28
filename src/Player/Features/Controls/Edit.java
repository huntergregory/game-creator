package Player.Features.Controls;

import Player.PlayerMain.PlayerStage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Edit extends Control {
    /**
     * Creates a button instance
     */
    public Edit(PlayerStage context, String gameName) {
        super(context, gameName,"Edit", "Press here to edit " + gameName);
    }

    @Override
    protected EventHandler<MouseEvent> action() {
        return event -> myContext.edit(myGameName);
    }
}
