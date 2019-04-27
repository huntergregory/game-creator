package Launcher;

import Engine.src.Triggers.Events.Event;
import GameCenter.main.GameCenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class LauncherController {
    public Button createAccountButton;
    public Button forgotPasswordButton;
    public Button goBackButton;
    public PasswordField confirmPassword;
    public PasswordField password;
    public TextField email;
    public TextField username;
    public Button loginButton;
    public Button createButton;


    @FXML
    private void createAccount() {
        creatingAccount(true);
    }

    @FXML
    private void goBack() {
        creatingAccount(false);
    }

    @FXML
    private void login(ActionEvent event) throws Exception {
        // TODO: write code for logging in
        try {
            new GameCenter().start(new Stage());
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch(IOException e) {
            System.out.println("Could not open GameCenter");
        }
    }

    @FXML
    private void create() {
        // TODO: write code for creating an account
    }

    private void creatingAccount(boolean bool) {
        forgotPasswordButton.setVisible(!bool);
        createAccountButton.setVisible(!bool);
        loginButton.setVisible(!bool);
        createButton.setVisible(bool);
        goBackButton.setVisible(bool);
        confirmPassword.setVisible(bool);
        email.setVisible(bool);
    }
}
