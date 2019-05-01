package network_account;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller for the identity pane that shows information about the user after they have successfully logged in,
 * including display name and high scores for the games in the social center.
 */
public class IdentityController {
    private List<Label> myScores;
    private String myCurrentGame;

    @FXML
    public Label usernameText, score1, score2, score3;
    public ImageView avatarImageView;

    public IdentityController(){
        myScores = new ArrayList<>();
        myScores.add(score1);
        myScores.add(score2);
        myScores.add(score3);
        myCurrentGame = "mygame1";
    }

    public void updateSocial(){
//        updateIdentity(myCurrentGame);
    }

//    private void updateIdentity(String gameName){
//        List<String> highScores = userIdentity.getHighScores(gameName);
//        for(int k = 0; k < 3; k++) {
//            try {
//                myScores.get(k).setText(highScores.get(k));
//            } catch (NullPointerException e){
//                myScores.get(k).setText("");
//            }
//        }
//    }
//
//    public void setGame(String game){
//        myCurrentGame = game;
//    }
}
