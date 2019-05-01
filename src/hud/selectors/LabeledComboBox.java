package hud.selectors;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * A ComboBox with a Label above it. Handles details of creating and handling the ComboBox under the hood.
 */
public abstract class LabeledComboBox extends LabeledVBox {
    private static final int NUM_OPTIONS_SHOWN = 4;

    private ComboBox<String> myDropBox;

    /**
     * Create a LabeledComboBox
     * @param labelText
     */
    public LabeledComboBox(String labelText) {
        super(labelText);
    }

    /**
     * @return String items to be displayed in ComboBox
     */
    abstract protected List<String> getItems();

    /**
     * @return index of selected item
     */
    protected int getSelectedIndex() {
        if (myDropBox == null)
            return 0;
        return myDropBox.getSelectionModel().getSelectedIndex();
    }

    private void constructDropBox() {
        myDropBox = new ComboBox<>();
        myDropBox.setEditable(false);
        myDropBox.itemsProperty().set(FXCollections.observableArrayList(getItems()));
        myDropBox.setVisibleRowCount(NUM_OPTIONS_SHOWN);
        myDropBox.getSelectionModel().selectFirst();
    }

    @Override
    protected Node getMainComponent() {
        if (myDropBox == null)
            constructDropBox();
        return myDropBox;
    }
}
