package Player.Features.Sliders;

import Player.PlayerMain.PlayerStage;

/**
 * This class is used to set the number of lives during a game.
 * @author Carter Gay
 */
public class LivesSlider extends PlayerSlider {

    private static final String TITLE = "Lives";

    /**
     * Constructor for the LivesSlider class
     * @param playerStage
     */
    public LivesSlider(PlayerStage playerStage) {
        super(playerStage);
    }

    @Override
    protected void handleItemSelected(Number item) {
        int myLives = (int)item.doubleValue();
        myPlayerStage.updateLives(myLives);
    }

    @Override
    protected double[] getMinMaxCurrentVals() {
        return DEFAULT_MIN_MAX_CURRENT;
    }
}
