package uiutils.panes;

import static auth.Styles.TOP_PANE_STYLE;

public class TopPane extends Pane {
    public TopPane(double x, double width, double height) {
        super(x, 0, width, height, TOP_PANE_STYLE);
    }
    public TopPane(double x, double width, double height, String ID) {
        super(x, 0, width, height, TOP_PANE_STYLE, ID);
    }
}

