package auth.auth_ui_components;

import auth.Callback;
import javafx.scene.image.Image;

/**
 * Represents an Icon that uses an image as its background.
 *
 * @author Anshu Dwibhashi
 */
public class ImageIcon extends Icon {
    @Override
    public void select() {

    }

    @Override
    public void deselect() {

    }
        public ImageIcon(Image img, String tooltipText, Callback onClickCallback) {
        super(img, tooltipText, onClickCallback);
    }
}
