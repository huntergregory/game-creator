package Player.Features.Selectors;

import Player.Features.Feature;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public abstract class Selector extends Feature {
    private static final int NUM_OPTIONS_SHOWN = 4;

    ComboBox<String> myDropBox;

    /**
     * Constructor for Selector object
     */
    public Selector() {
        myDropBox = new ComboBox<>();
        myDropBox.setEditable(false);
        myDropBox.itemsProperty().set(getItemList());
        myDropBox.setVisibleRowCount(NUM_OPTIONS_SHOWN);
        myDropBox.getSelectionModel().selectFirst();
        myDropBox.valueProperty().addListener((o, oldVal, newVal) -> handleItemSelected(newVal));
    }

    abstract protected ObservableList<String> getItemList();

    abstract protected void handleItemSelected(String item);

    /**
     * Return the dropbox of the Selector
     *
     * @return myDropBox
     */
    @Override
    public Node getMainComponent() {
        return myDropBox;
    }
}
