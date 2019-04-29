package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SceneScriptController {
    @FXML
    public TextField sceneScript;

    public String script;

    public void addSceneScript(){
        script = sceneScript.getText();
        sceneScript.getScene().getWindow().hide();
    }
}
