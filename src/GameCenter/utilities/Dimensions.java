package GameCenter.utilities;

public class Dimensions {
    public static final double GC_WINDOW_WIDTH = 750;
    public static final double GC_WINDOW_HEIGHT = 500;
    public static final double GC_RIGHT_PANE_WIDTH = 250;
    public static final double GC_RIGHT_PANE_HEIGHT = 450;
    private static final double GC_MARGIN = 25;
    public static final double GC_RIGHT_PANE_X = GC_WINDOW_WIDTH - GC_RIGHT_PANE_WIDTH - GC_MARGIN;
    public static final double GC_RIGHT_PANE_Y = (GC_WINDOW_HEIGHT - GC_RIGHT_PANE_HEIGHT) / 2;
    public static final double GC_TITLE_TEXT_X = GC_MARGIN + 18.5;
    public static final double GC_TITLE_TEXT_Y = GC_MARGIN + 26;
    public static final double GC_TITLE_PANE_X = GC_MARGIN;
    public static final double GC_TITLE_PANE_Y = GC_MARGIN;
    public static final double GC_TITLE_PANE_WIDTH = 150;
    public static final double GC_TITLE_PANE_HEIGHT = 35;
    public static final double GC_PERMA_PANE_Y = GC_TITLE_PANE_Y + GC_TITLE_PANE_HEIGHT + GC_MARGIN;
    public static final double GC_PERMA_PANE_HEIGHT = GC_WINDOW_HEIGHT - GC_PERMA_PANE_Y - GC_MARGIN;
    public static final double GC_PERMA_PANE_WIDTH = GC_WINDOW_WIDTH - GC_RIGHT_PANE_WIDTH - (3 * GC_MARGIN);
    public static final double GC_PERMA_CONTENT_OFFSET = 25;
    public static final double BUTTON_OFFSET_CORRECTION = 8;
    public static final double THUMBNAIL_WIDTH = 225;
    public static final double THUMBNAIL_HEIGHT = 180;
    public static final double THUMBNAIL_MARGIN = 25;
    public static final double GC_DESC_TEXT_X = GC_TITLE_PANE_X + GC_PERMA_PANE_WIDTH/2;
    public static final double GC_DESC_TEXT_Y = GC_PERMA_PANE_Y + GC_PERMA_PANE_HEIGHT/2;
    public static final double ACTIONS_MARGIN = 25;
}
