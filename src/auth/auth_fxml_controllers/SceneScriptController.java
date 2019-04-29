package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SceneScriptController {
    @FXML
    public TextField sceneScript;


    public void addSceneScript(){
        String script = sceneScript.getText();
        System.out.println(script);
    }
}
