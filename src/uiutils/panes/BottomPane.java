package uiutils.panes;

import static auth.Dimensions.ENV_WINDOW_HEIGHT;
import static auth.Styles.BOTTOM_PANE_STYLE;

public class BottomPane extends Pane {
    public BottomPane(double x, double width, double height) {
        super(x, ENV_WINDOW_HEIGHT - height, width, height, BOTTOM_PANE_STYLE);
    }
    public BottomPane(double x, double width, double height, String ID) {
        super(x, ENV_WINDOW_HEIGHT - height, width, height, BOTTOM_PANE_STYLE, ID);
    }
}
