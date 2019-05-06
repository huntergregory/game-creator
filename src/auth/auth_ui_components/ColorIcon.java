package auth.auth_ui_components;

import auth.Callback;
import javafx.scene.paint.Color;

/**
 * This class is represents an icon in the color palette folder.
 *
 * @author Anshu Dwibhashi
 */
public class ColorIcon extends Icon {
    @Override
    public void select() {

    }

    @Override
    public void deselect() {

    }
        public ColorIcon(Color color, String tooltipText, Callback onClickCallback) {
        super(color, tooltipText, onClickCallback);
    }
}
