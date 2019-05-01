package Player.Features.Sliders;

/**
 * This class is used to set the number of lives during a game.
 * @author Carter Gay
 */
public class LivesSlider extends PlayerSlider {

    private static final String TITLE = "Lives";

    @Override
    protected void handleItemSelected(Number item) {
        double myLives = item.doubleValue();
    }

    @Override
    protected double[] getMinMaxCurrentVals() {
        return DEFAULT_MIN_MAX_CURRENT;
    }
}
