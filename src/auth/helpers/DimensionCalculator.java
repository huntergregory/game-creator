package auth.helpers;

import javafx.scene.layout.Region;

import static auth.Dimensions.ENV_WINDOW_HEIGHT;
import static auth.Dimensions.ENV_WINDOW_WIDTH;

/**
 * This class helps calculating dimensions for certain JavaFX nodes.
 *
 * @author Anshu Dwibhashi
 */
public class DimensionCalculator {
    /**
     * Calculates the position to vertically center an object with height: height.
     *
     * @param height The height of a JavaFX object.
     * @return The position that would vertically center an object.
     */
    public static double centreVertical(double height) {
        return (ENV_WINDOW_HEIGHT / 2.0) - (height / 2.0);
    }

    /**
     * Calculates the position to horizontally center an object with width: width.
     *
     * @param width The width of an object
     * @return The position
     */
    public static double centreHorizontal(double width) {
        return (ENV_WINDOW_WIDTH / 2.0) - (width / 2.0);
    }

    public static double computeRightEdge(double width) {
        return (ENV_WINDOW_WIDTH) - (width);
    }

    public static double computeMarginToBottomEdge(Region region, double desiredMargin) {
        return region.getLayoutY() + region.getPrefHeight() + desiredMargin;
    }
}
