package uiutils.panes;

import static auth.Dimensions.LEFT_EDGE;
import static auth.Styles.LEFT_PANE_STYLE;

public class LeftPane extends Pane {
    public LeftPane(double y, double width, double height) {
        super(LEFT_EDGE, y, width, height, LEFT_PANE_STYLE);
    }
    public LeftPane(double y, double width, double height, String ID) {
        super(LEFT_EDGE, y, width, height, LEFT_PANE_STYLE, ID);
    }
}