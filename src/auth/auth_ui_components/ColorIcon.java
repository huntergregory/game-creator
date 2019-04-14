package auth.auth_ui_components;

import auth.Callback;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class ColorIcon extends Icon {
    public ColorIcon(Color color, String tooltipText, Callback onClickCallback) {
        super(color, tooltipText, onClickCallback);
    }
}
