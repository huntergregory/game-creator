package hud;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * A Heads Up Display utility with customizable data and an optional Plotter for visualizing data.
 * Game loop is expected to store data in DataTrackers and call update on the HUDView to reflect the current values of data being stored.
 * The HUDView passes along any NumericalDataTrackers to the Plotter.
 *
 * @author Hunter Gregory
 * @author Carter Gay
 */
public class HUDView {
    private static final String TITLE_ID_CSS = "hud-title";
    private static final String DATA_LABEL_CLASS_CSS = "data-label";
    private static final String SCROLL_PANE_CLASS_CSS = "scroll-pane";
    private static final double INTER_VALUES_SPACING = 2;
    private static final double PLOT_VALUES_SPACING = 20;
    private static final String PLOTS_HIDDEN_TEXT = "Show Plot";
    private static final String PLOTS_SHOWING_TEXT = "Hide Plot";

    private DataTracker[] myTrackers;
    private Label[] myDataLabels;
    private Label myTitle;
    private ScrollPane myScrollPane;
    private VBox myHudValuesBox;
    private VBox myPlotAndValuesBox;
    private Plotter myPlotter;
    private boolean myPlotsIncluded = false;
    private Button myPlotToggleButton;

    /**
     * Create a HUDView
     * @param width
     * @param height
     * @param title
     * @param includePlots
     * @param trackers
     */
    public HUDView(double width, double height, String title, boolean includePlots, DataTracker ... trackers) {
        myTrackers = trackers;
        createVBoxes();
        addToggle();
        addLabels(title);
        createScrollPane(width, height);
        myPlotter = new Plotter(width, height, filterTrackers(myTrackers));
        setPlotsIncluded(includePlots);
        update();
    }

    /**
     * @return the HUD display
     */
    public Node getNode() {
        return myScrollPane;
    }

    /**
     * Updates the values displayed in the HUD and updates any plots if applicable.
     */
    public void update() {
        clearText();
        for (int k = 0; k< myTrackers.length; k++) {
            var tracker = myTrackers[k];
            String value = tracker.size() == 0 ? "" : tracker.getLatestValue().toString();
            myDataLabels[k].setText(tracker.getDataName() + ": " + value);
        }
        if (myPlotter != null) {
            myPlotter.updateGraph();
        }
    }

    /**
     * Update the title of the HUD
     * @param title
     */
    public void setNewTitle(String title) {
        myTitle.setText(title);
    }

    private NumericalDataTracker[] filterTrackers(DataTracker[] trackers) {
        return Arrays.stream(trackers).filter(tracker -> tracker instanceof NumericalDataTracker).toArray(NumericalDataTracker[]::new);
    }

    private void createVBoxes() {
        myHudValuesBox = new VBox();
        myHudValuesBox.setSpacing(INTER_VALUES_SPACING);
        myPlotAndValuesBox = new VBox(myHudValuesBox);
        myPlotAndValuesBox.setSpacing(PLOT_VALUES_SPACING);
    }

    private void addToggle() {
        myPlotToggleButton = new Button();
        myPlotToggleButton.setOnAction(e -> togglePlotsIncluded());
        updateButtonAppearance();
        myPlotAndValuesBox.getChildren().add(myPlotToggleButton);
    }

    private void togglePlotsIncluded() {
        setPlotsIncluded(!myPlotsIncluded);
    }

    private void setPlotsIncluded(boolean include) {
        if (include && !myPlotsIncluded)
            myPlotAndValuesBox.getChildren().add(myPlotter.getNode());
        else if (!include && myPlotsIncluded)
            myPlotAndValuesBox.getChildren().remove(myPlotter.getNode());
        myPlotsIncluded = include;
        updateButtonAppearance();
    }

    private void updateButtonAppearance() {
        if (!myPlotsIncluded)
            myPlotToggleButton.setText(PLOTS_HIDDEN_TEXT);
        else
            myPlotToggleButton.setText(PLOTS_SHOWING_TEXT);
    }

    private void addLabels(String title) {
        myTitle = new Label(title);
        myTitle.setStyle(DATA_LABEL_CLASS_CSS);
        myTitle.setId(TITLE_ID_CSS);
        myHudValuesBox.getChildren().add(myTitle);

        myDataLabels = new Label[myTrackers.length];
        for (int k = 0; k< myDataLabels.length; k++) {
            var label = new Label();
            label.setStyle(DATA_LABEL_CLASS_CSS);
            myHudValuesBox.getChildren().add(label);
            myDataLabels[k] = label;
        }
    }

    private void createScrollPane(double width, double height) {
        myScrollPane = new ScrollPane(myPlotAndValuesBox);
        myScrollPane.setStyle(SCROLL_PANE_CLASS_CSS);
        setDimensions(myScrollPane, width, height);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
    }

    private void setDimensions(Region region, double width, double height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setMinWidth(width);
        region.setMaxWidth(width);
    }

    private void clearText() {
        Arrays.stream(myDataLabels).forEach(label -> label.setText(""));
    }
}
