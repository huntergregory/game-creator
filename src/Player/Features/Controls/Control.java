package Player.Features.Controls;

import Player.PlayerMain.PlayerStage;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 * Abstract superclass to serve as template for all buttons/controls. Should be used for creating new Button gameObjects.
 *
 * @author Januario Carreiro
 */
public abstract class Control{
    Button myButton;
    PlayerStage myContext;
    String myGameName;

    /**
     * Creates a button instance
     */
    public Control(PlayerStage context, String gameName, String text, String tooltipText) {
        this.myButton = new Button();
        this.myContext = context;
        this.myGameName = gameName;

        myButton.setText(text);
        myButton.setTooltip(new Tooltip(tooltipText));
        myButton.setOnMouseClicked(action());
    }

    protected abstract EventHandler<MouseEvent> action();

    /**
     * Returns button object
     *
     * @return this button
     */
    public Button getButton() {
        return myButton;
    }
}
