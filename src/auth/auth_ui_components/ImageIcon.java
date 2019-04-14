package auth.auth_ui_components;

import auth.Callback;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class ImageIcon extends Icon {
    public ImageIcon(Image img, String tooltipText, Callback onClickCallback) {
        super(img, tooltipText, onClickCallback);
    }
}
