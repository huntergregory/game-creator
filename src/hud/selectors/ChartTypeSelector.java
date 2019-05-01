package hud.selectors;


import javafx.scene.chart.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A LabeledComboBox that allows a user to specify which XYChart it would like to use.
 * @author Hunter Gregory
 */
public class ChartTypeSelector extends LabeledComboBox {
    private static final String DESCRIPTION = "Select Chart Type";
    private static final List<Class<? extends XYChart>> CHARTS = List.of(StackedAreaChart.class,
                                                                           LineChart.class,
                                                                           ScatterChart.class,
                                                                           BubbleChart.class);

    /**
     * Create a ChartTypeSelector
     */
    public ChartTypeSelector() {
        super(DESCRIPTION);
    }

    @Override
    protected List<String> getItems() {
        return CHARTS.stream().map(clazz -> getClassName(clazz)).collect(Collectors.toList());
    }

    /**
     * @return class of chart type selected
     */
    public Class<? extends XYChart> getSelectedChart() {
        return CHARTS.get(getSelectedIndex());
    }

    private String getClassName(Class clazz) {
        System.out.println(clazz.toString());
        String[] splitPath = clazz.toString().split("\\.");
        return splitPath[splitPath.length - 1];
    }
}
