package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ObjectScriptController {
    @FXML
    public TextField objectScript;

    public String myScript;

    public void addObjectScript(){
        myScript = objectScript.getText();
        objectScript.getScene().getWindow().hide();
    }
}
