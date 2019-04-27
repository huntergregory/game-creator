package hud.selectors;

import hud.NumericalDataTracker;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * A LabeledVBox that contains a checkbox associated with each NumericalDataTracker.
 *
 * @author Hunter Gregory
 */
public class YAxisSelector extends LabeledVBox {
    private static final String DESCRIPTION = "Y data";

    private VBox myVBox;
    private ArrayList<CheckBox> myCheckBoxes;
    private NumericalDataTracker[] myTrackers;

    /**
     * Create a YAxisSelector
     * @param trackers
     */
    public YAxisSelector(NumericalDataTracker ... trackers) {
        super(DESCRIPTION);
        myTrackers = trackers;
        constructVBox();
    }

    private void constructVBox() {
        myVBox = new VBox();
        myCheckBoxes = new ArrayList<>();
        for (NumericalDataTracker tracker : myTrackers) {
            var checkbox = new CheckBox(tracker.getDataName());
            checkbox.setAllowIndeterminate(false);
            myCheckBoxes.add(checkbox);
            myVBox.getChildren().add(checkbox);
        }
    }

    /**
     * @return list of DataTrackers currently selected
     */
    public NumericalDataTracker[] getSelectedTrackers() {
        List<NumericalDataTracker> selectedTrackers = new ArrayList<>();
        for (int k=0; k<myTrackers.length; k++) {
            if (myCheckBoxes.get(k).isSelected())
                selectedTrackers.add(myTrackers[k]);
        }
        return selectedTrackers.toArray(new NumericalDataTracker[0]);
    }

    @Override
    protected Node getMainComponent() {
        return myVBox;
    }
}
