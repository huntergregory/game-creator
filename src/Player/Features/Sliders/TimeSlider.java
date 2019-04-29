package Player.Features.Sliders;

import Player.PlayerMain.PlayerStage;

/**
 * This class is used to set the time limit during a game.
 * @author Carter Gay
 */
public class TimeSlider extends PlayerSlider {

    private static final String TITLE = "Time";

    public TimeSlider(PlayerStage playerStage) {
        super(playerStage);
    }

    @Override
    protected void handleItemSelected(Number item) {
        int myTime = (int)item.doubleValue();
        myPlayerStage.updateTime(myTime);
    }

    @Override
    protected double[] getMinMaxCurrentVals() {
        return DEFAULT_MIN_MAX_CURRENT;
    }
}
