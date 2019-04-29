package auth.helpers;

import javafx.scene.layout.Pane;

public class RectangleHelpers {
    public static Pane createStyledRectangle(double x, double y, double width, double height, String style) {
        var rect = new Pane();
        rect.setLayoutX(x); rect.setLayoutY(y); rect.setPrefSize(width, height);
        rect.setStyle(style);
        return rect;
    }
}
