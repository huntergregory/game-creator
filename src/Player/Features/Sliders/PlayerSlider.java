package Player.Features.Sliders;

import Player.Features.Feature;
import Player.PlayerMain.PlayerStage;
import javafx.scene.Node;
import javafx.scene.control.Slider;

/**
 * This class can be used to add any type of slider to a GUI
 * @author Carter Gay
 */
public abstract class PlayerSlider extends Feature {
    static final double[] DEFAULT_MIN_MAX_CURRENT = {0, 5, 1};
    public PlayerStage myPlayerStage;
    private Slider mySlider;

    /**
     * Constructor for the PlayerSlider object
     * @param playerStage
     */
    PlayerSlider(PlayerStage playerStage) {
        myPlayerStage = playerStage;
        double[] minMaxCurrent = getMinMaxCurrentVals();
        if (minMaxCurrent == null || minMaxCurrent.length != 3)
            minMaxCurrent = DEFAULT_MIN_MAX_CURRENT;
        mySlider = new Slider(minMaxCurrent[0], minMaxCurrent[1], minMaxCurrent[2]);
        mySlider.setMinorTickCount(0);
        mySlider.setMajorTickUnit(1.0f);
        mySlider.setSnapToTicks(true);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.valueProperty().addListener((ov, old_val, new_val) -> handleItemSelected(new_val));
    }

    abstract protected void handleItemSelected(Number item);

    abstract protected double[] getMinMaxCurrentVals();

    @Override
    /**
     * Get the slider
     */
    public Node getMainComponent() {
        return mySlider;
    }

}
