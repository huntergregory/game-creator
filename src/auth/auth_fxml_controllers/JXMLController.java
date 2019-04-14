package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import javafx.scene.layout.Pane;

public abstract class JXMLController {
    protected CanvasScreen context;

    public void initData(Pane propsPane, CanvasScreen context) {
        propsPane.requestFocus();
        this.context = context;
    }
}
