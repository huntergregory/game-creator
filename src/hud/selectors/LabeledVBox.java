package hud.selectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A VBox with a label above a Node (label text and Node specified in subclass).
 *
 * @author Hunter Gregory
 */
public abstract class LabeledVBox {
    private VBox myVBox;
    private String myLabelText;

    /**
     * Create a LabeledVBox
     * @param labelText
     */
    public LabeledVBox(String labelText) {
        myLabelText = labelText;
    }

    /**
     * @return VBox with label and node
     */
    public VBox getVBox() {
        if (myVBox == null) {
            var label = new Label(myLabelText);
            myVBox = new VBox(label, getMainComponent());
        }
        return myVBox;
    }

    /**
     * @return Node to be displayed under label
     */
    abstract protected Node getMainComponent();
}
