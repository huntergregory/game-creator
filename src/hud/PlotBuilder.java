package hud;

import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

/**
 * This class is used to construct plots to dynamically display game variables
 * @author Carter Gay
 * @author Hunter Gregory
 */
public class PlotBuilder {
    private static final String CHOOSE_Y_MESSAGE = "Please select y";

    private NumericalDataTracker<Double> myX;
    private NumericalDataTracker<Double>[] myY;
    private double myWidth;
    private double myHeight;
    private NumberAxis myXAxis;
    private NumberAxis myYAxis;
    private Class<? extends XYChart> myChartClass;


    /**
     * PlotBuilder constructor that takes in an x feature and any number of y features
     * @param width
     * @param height
     * @param x
     * @param y
     */
    public PlotBuilder( double width, double height, Class<? extends XYChart> chartClass, NumericalDataTracker x, NumericalDataTracker ... y) {
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myXAxis = new NumberAxis();
        myXAxis.setLabel(x.getDataName());
        myYAxis = new NumberAxis();
        myChartClass = chartClass;
    }

    /**
     * Creates the scatter plot of the passed in x and y features with appropriate titles/labels
     * @return
     */
    public XYChart<Number,Number> createPlot() {
        XYChart myPlot;
        try {
            myPlot = myChartClass.getDeclaredConstructor(Axis.class, Axis.class).newInstance(myXAxis, myYAxis);
        }
        catch (Exception e) { //TODO incorporate Professor Duvall's reflection utility (like ReflectionException)
            myPlot = new ScatterChart<>(myXAxis, myYAxis);
        }

        setChartAttributes(myPlot);
        if (myY.length != 0)
            plotData(myPlot);
        else
            myPlot.setTitle(CHOOSE_Y_MESSAGE);
        return myPlot;
    }

    private void setChartAttributes(XYChart myPlot) {
        myPlot.setMaxWidth(myWidth);
        myPlot.setMaxHeight(myHeight);
        myPlot.setAnimated(false);
    }

    private void plotData(XYChart<Number, Number> myPlot) {
        String yString = myY[0].getDataName();
        for (int k = 1; k < myY.length; k++) {
            yString += ", " + myY[k].getDataName();
        }
        myYAxis.setLabel(yString);
        myPlot.setTitle(yString + " vs " + myX.getDataName());
        var xData = myX.getData();
        for (int i = 0; i < myY.length; i++) {
            var series = new XYChart.Series();
            series.setName(myY[i].getDataName());
            var yData = myY[i].getData();
            for (int j = 0; j < myX.getData().size() && j < myY[i].getData().size(); j++) {
                series.getData().add(new XYChart.Data(xData.get(j), yData.get(j)));
            }
            myPlot.getData().addAll(series);
        }
    }

}
