package uiutils.panes;

import static auth.Styles.RIGHT_PANE_STYLE;
import static auth.helpers.DimensionCalculator.computeRightEdge;

public class RightPane extends Pane {
    public RightPane(double y, double width, double height) {
        super(computeRightEdge(width), y, width, height, RIGHT_PANE_STYLE);
    }
    public RightPane(double y, double width, double height, String ID) {
        super(computeRightEdge(width), y, width, height, RIGHT_PANE_STYLE, ID);
    }
}
