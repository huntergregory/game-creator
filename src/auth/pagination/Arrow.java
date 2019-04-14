package auth.pagination;

import auth.Callback;
import auth.UIElement;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static auth.Dimensions.ARROW_SIZE;
import static auth.Strings.*;


public abstract class Arrow implements UIElement {
    private ImageView arrowImage;
    public Arrow(Image arrowImage) {
        this.arrowImage = new ImageView(arrowImage);
        this.arrowImage.setFitHeight(ARROW_SIZE);
        this.arrowImage.setFitWidth(ARROW_SIZE);
        this.arrowImage.setCursor(Cursor.HAND);
    }
    public void onClick(Callback callback) {
        arrowImage.setOnMouseClicked(e -> {
            try {
                callback.onCallback();
            } catch (Exception ex) {
                // Do nothing on arrow click if a valid method is not provided
            }
        });
        arrowImage.setPickOnBounds(true);
    }

    @Override
    public Node getView() {
        return arrowImage;
    }

    @Override
    public String getID() {
        return DEFAULT_ID;
    }
}
