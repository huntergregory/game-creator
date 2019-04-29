package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ObjectScriptController {
    @FXML
    public TextField objectScript;

    public void addObjectScript(){
        String script = objectScript.getText();
    }
}
