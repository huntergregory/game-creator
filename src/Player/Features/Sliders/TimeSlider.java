package Player.Features.Sliders;

/**
 * This class is used to set the time limit during a game.
 * @author Carter Gay
 */
public class TimeSlider extends PlayerSlider {

    private static final String TITLE = "Time";

    @Override
    protected void handleItemSelected(Number item) {
        double myLevelTime = item.doubleValue();
    }

    @Override
    protected double[] getMinMaxCurrentVals() {
        return DEFAULT_MIN_MAX_CURRENT;
    }
}
