package hud;

import hud.selectors.ChartTypeSelector;
import hud.selectors.XAxisSelector;
import hud.selectors.YAxisSelector;
import javafx.scene.chart.XYChart;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A GUI and plot combination. The user can specify the data for the x axis, multiple data for the y axis, and the type of chart to display.
 * Doesn't plot any categorical data (strictly uses NumericalDataTrackers).
 *
 * @author Hunter Gregory
 */
public class Plotter {
    private static final double VERTICAL_SPACING = 20;
    private static final double HORIZONTAL_SPACING = 25;

    private XAxisSelector myXAxisSelector;
    private YAxisSelector myYAxisSelector;
    private ChartTypeSelector myChartSelector;
    private VBox myVBox;
    private double myWidth;
    private double myHeight;

    /**
     * Create a Plotter with a given array of possible data to plot.
     * @param width
     * @param height
     * @param trackers
     */
    public Plotter(double width, double height, NumericalDataTracker ... trackers) {
        myWidth = width;
        myHeight = height;
        myXAxisSelector = new XAxisSelector(trackers);
        myYAxisSelector = new YAxisSelector(trackers);
        myChartSelector = new ChartTypeSelector();
        setupDisplay();
        updateGraph();
    }

    /**
     * Update graph with new data tracker values
     */
    public void updateGraph() {
        myVBox.getChildren().set(0, getCurrentGraph());
    }

    /**
     * @return display including plot and mini GUI
     */
    public Node getNode() {
        return myVBox;
    }

    private void setupDisplay() {
        var axesSelection = new HBox(myYAxisSelector.getVBox(), myXAxisSelector.getVBox());
        axesSelection.setSpacing(HORIZONTAL_SPACING);
        axesSelection.setAlignment(Pos.CENTER);

        var chartVBox = myChartSelector.getVBox();
        chartVBox.setAlignment(Pos.CENTER);

        myVBox = new VBox(getCurrentGraph(), chartVBox, axesSelection);
        myVBox.setSpacing(VERTICAL_SPACING);
    }

    private XYChart<Number, Number> getCurrentGraph() {
        var plotBuilder = new PlotBuilder(myWidth, myHeight, myChartSelector.getSelectedChart(), myXAxisSelector.getSelectedTracker(), myYAxisSelector.getSelectedTrackers());
        return plotBuilder.createPlot();
    }
}
