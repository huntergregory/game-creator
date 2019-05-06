package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import javafx.scene.layout.Pane;

/**
 * Every Controller that takes in input uses this class to set the correct canvas screen as well as make sure the current
 * pane is the pane taking in inputs.
 *
 * @author Anshu Dwibhashi
 */
public abstract class JXMLController {
    protected CanvasScreen context;

    /**
     * Sets the current CanvasScreen to be the one being displayed. Also makes sure that propsPane is in focus to recieve
     * inputs.
     *
     * @param propsPane The Pane to be in focus.
     * @param context The current canvas being displayed.
     */
    public void initData(Pane propsPane, CanvasScreen context) {
        propsPane.requestFocus();
        this.context = context;
    }
}
