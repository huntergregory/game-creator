package auth.auth_fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyEventController {
    @FXML
    public Button addButton;
    public TextField keyTextField, scriptTextField, idTextField;

    public void setKey(KeyEvent e){
        if(!e.getCode().equals(KeyCode.TAB)) {
            keyTextField.setText("");
        }
    }

    public void addKeyElement(){
        String script = idTextField.getText()+".setOnKeyPressed(e->{ if(keyCode.equals(KeyCode." + keyTextField.getText() + "){" + scriptTextField.getText() + "}};";
        System.out.println(script);
    }
}
