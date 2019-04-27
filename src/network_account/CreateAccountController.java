package network_account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * FXML controller for the pane that allows users to create their own account. Links with the Google App Engine backend
 * to create and save user information
 */
public class CreateAccountController {
    @FXML
    public TextField emailTextField, usernameTextField, displayNameTextField, passwordField1, passwordField2;
    public Label failLabel;
    public Button createAccountButton;

    /**
     * Creates an account with all of the information requisite to form a user identity if all of the entered
     * information is valid; else clears all TextFields and informs the user that their information was invalid.
     */
    public void createAccount(){
        failLabel.setText("Invalid account info");
        try {
            if(!passwordField1.getText().equals(passwordField2.getText())){
                throw new RuntimeException("Password error");
            }
            String createRequest = "http://tmtp-spec.appspot.com/register?username=" + usernameTextField.getText() +
                    "&name=" + displayNameTextField.getText() + "&password=" + passwordField1.getText() + "&email=" +
                    emailTextField.getText();
            JsonObject jObject = createResponse(createRequest);
            failLabel.setText(jObject.get("resultDesc").toString().replaceAll("^\"|\"$", ""));

            TextField[] allTextFields = new TextField[]{emailTextField, usernameTextField, displayNameTextField,
                    passwordField1, passwordField2};
            for(TextField tf:allTextFields){
                tf.setText("");
            }
        } catch (MalformedURLException e) {
            failLabel.setText("Server unavailable");
        } catch (IOException e) {
            failLabel.setText("Couldn't connect");
        } catch(RuntimeException e){
            failLabel.setText("Passwords must match");
        }

    }

    /**
     * Gets the Json object that signifies the response to a web request for the account
     * @param createRequest is the String that signifies the address for the web request
     * @return a Json object telling whether the request was successful or not
     * @throws IOException
     */
    public JsonObject createResponse(String createRequest) throws IOException {
        URL url = new URL(createRequest);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jParser = new JsonParser();
        JsonElement jElement = jParser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jObject = jElement.getAsJsonObject();

        return jObject;
    }

    /**
     * Attempt to create the account if the user presses enter while in the password confirmation textfield
     * @param e is the key that is pressed
     */
    public void createAccountEnter(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            createAccount();
        }
    }
}
