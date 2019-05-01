package network_account;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

// TODO: add game cycling functionality for the identity pane

/**
 * Class made to manage the identity of the user and hold all of the information of the player
 */
public class IdentityManager {
    private UserIdentity myIdentity;
    private FXMLLoader myLoginLoader;
    private FXMLLoader myIdentityLoader;
    private List<Label> myScores;
    private Stage myPrimaryStage = new Stage();

    /**
     * Constructor setting the UserIdentity to a default
     */
    public IdentityManager(){
        myIdentity = new UserIdentity();
        myLoginLoader = new FXMLLoader();
        myIdentityLoader = new FXMLLoader();
        myScores = new ArrayList<>();
    }

    /**
     * Set the input stage to the login screen
     */
    public void setStageLogin(){
        try {
            myLoginLoader.setLocation(IdentityManager.class.getResource("/network_fxml/login.fxml"));
            Parent root = myLoginLoader.load();
            myPrimaryStage.setScene(new Scene(root));
            myPrimaryStage.setResizable(false);
            myPrimaryStage.show();
        } catch (IOException e) {
            System.out.println("Login FXML file not found");
        }
    }

    /**
     * Add a new high score for a certain game under the current user's ID
     * @param gameID is the name of the game
     * @param highScore is the score for that certain game
     */
    public void addHighScore(String gameID, String highScore){
        UserIdentity identity = getIdentity();
        if(!identity.getName().equals("")){
            String scoreString = "http://tmtp-spec.appspot.com/newHighScore?username=" + identity.getUsername() +
                    "&gameID=" + gameID + "&score=" + highScore;
            try {
                URL url = new URL(scoreString);
                URLConnection request = url.openConnection();
                request.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the identity loaded by the login loader, if applicable
     * @return the UserIdentity: may still be default identity
     */
    public UserIdentity getIdentity(){
        try {
            LoginController loginController = myLoginLoader.getController();
            myIdentity = loginController.getMyIdentity();
        } catch(NullPointerException e){
            myIdentity = new UserIdentity();
        }
        return myIdentity;
    }

    private void setStage(Parent root){
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void updateIdentity(String gameName){
        List<String> highScores = myIdentity.getHighScores(gameName);
        for(int k = 0; k < 3; k++) {
            try {
                myScores.get(k).setText(highScores.get(k));
            } catch (NullPointerException e){
                myScores.get(k).setText("");
            }
        }
    }
}
