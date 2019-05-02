package uiutils.panes;

import auth.UIElement;

import static auth.Strings.DEFAULT_ID;
import static auth.helpers.RectangleHelpers.createStyledRectangle;

public abstract class Pane implements UIElement {
    private javafx.scene.layout.Pane view;
    private String ID = DEFAULT_ID;
    public Pane(double x, double y, double width, double height, String style) {
        view = createStyledRectangle(x, y, width, height, style);
    }
    public Pane(double x, double y, double width, double height, String style, String ID) {
        this(x, y, width, height, style);
        this.ID = ID;
    }
    public javafx.scene.layout.Pane getView() {
        return view;
    }

    public String getID() {
        return ID;
    }
}
