package network_account;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * FXML Controller for the user login pane (res/network_fxml/loginpane.fxml), allowing user's to connect with the
 * Google App Engine backend to login and load information such as display name and personal high scores
 */
public class LoginController {
    @FXML
    public TextField usernameTextField, passwordTextField;
    public Label loginFailLabel;
    public Button loginButton;

    /**
     * Check to see if login information is valid: if it is not tell the user that the information they provided was
     * invalid and prompt again
     */
    public void login(){
        try {
            String webRequest = "http://tmtp-spec.appspot.com/login?username=" + usernameTextField.getText() +
                   "&password=" + passwordTextField.getText();
            getJsonReponse(webRequest);
            resetFields();
        } catch (MalformedURLException e) {
            loginFailLabel.setText("Couldn't connect to server");
        } catch (IOException e) {
            loginFailLabel.setText("Couldn't connect to internet");
        }
    }

    /**
     * Attempt to login if the user presses enter while their cursor is in the password textfield
     * @param e is the key that they press, which will result in inaction unless the key pressed is enter
     */
    public void loginEnter(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            login();
        }
    }

    /**
     * Send a password reset email to the username of choice
     */
    public void sendResetEmail(){
        try{
            String emailRequest = "http://tmtp-spec.appspot.com/reset?username=" + usernameTextField.getText();
            URL url = new URL(emailRequest);
            URLConnection request = url.openConnection();
            request.connect();
            loginFailLabel.setText("Password reset email sent");
            resetFields();
        } catch (IOException e) {
            loginFailLabel.setText("Could not send email");
        }
    }

    /**
     * Open the create account tab if the user clicks the "Create Account" button
     */
    public void openCreateAccount(){
        try{
            Parent root = FXMLLoader.load(RunAccount.class.getResource("/network_fxml/createaccount.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Create New Account");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(IOException e){
            System.out.println("Error in using create account fxml");
        }
    }

    private void resetFields(){
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    private void getJsonReponse(String request) throws IOException {
        CreateAccountController testController = new CreateAccountController();
        JsonObject jObject = testController.createResponse(request);
        loginFailLabel.setText(jObject.get("resultDesc").toString().replaceAll("^\"|\"$", ""));
    }
}
