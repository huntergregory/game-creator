package network_account;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * FXML LevelController for the identity pane that shows information about the user after they have successfully logged in,
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

}
