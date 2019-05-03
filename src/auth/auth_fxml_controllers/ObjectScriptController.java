package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ObjectScriptController {
    @FXML
    public TextField objectScript;
    public TextField objectID;
    public String script;
    public String objID;

    public void addObjectScript(){
        objID = objectID.getText();
        script = "." + objectScript.getText() + ";";
        objectScript.getScene().getWindow().hide();
    }
}
