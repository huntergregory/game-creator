package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CollisionController {
    @FXML
    public TextField object1ID, object2ID, scriptField;
    public String script;
    public void addCollision(){
        script = object1ID.getText() + ".addCollision(" + object2ID.getText() + ", \"" + scriptField.getText() + "\");";
        object1ID.getScene().getWindow().hide();
    }
}
