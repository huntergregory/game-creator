package GameCenter.main;

import GameCenter.gameData.DataParser;
import GameCenter.gameData.DataStruct;
import GameCenter.utilities.Thumbnail;
import Player.PlayerMain.PlayerStage;
import auth.RunAuth;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network_account.UserIdentity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static GameCenter.utilities.Constants.NUM_HIGH_SCORES;
import static GameCenter.utilities.Strings.GC_SCORE_EXCEPTIONS;
import static GameCenter.utilities.Strings.GC_SOCIAL_LABEL_STYLE;

/**
 * The LevelController for the GameCenter. Works in conjunction with GameCenter.java and GameCenter.fxml, which can be found
 * under the resources folder.
 *
 * This controller defines all actions that occur when a user interacts with the GUI. It also defines several parts of
 * the GUI that cannot be done in fxml, such as placing images parsed from a .json file.
 */
public class GameCenterController {
    private List<DataStruct> gameData;
    private ArrayList<Integer> favoriteGames;
    private int activeThumbnail;
    private int myIndex;
    private Number ratingVal;
    private ImageView activeGameImageView;
    UserIdentity myIdentity;

    @FXML
    public Pane socialPane, newGamePane, descriptionPane, ratingPane;
    public Pane favoritePane;
    public ScrollPane thumbPane;
    public GridPane friendPane;
    public Slider ratingSlider;
    public VBox thumbPaneContent;
    public Text titleText, descriptionText, ratingText, username;
    public Button newGameButton, playButton, editButton, rateButton, returnButton, favoriteButton;
    public Label score1, score2, score3;

    /**
     * Initializes several things that cannot be done through fxml alone such as importing thumbnails and setting
     * listeners for the rating. Takes a userIdentity parameter, which will later be passed to the authoring
     * environment so that the user is logged in throughout the program.
     *
     * @param userIdentity user Identity
     */
    public void initGameCenter(UserIdentity userIdentity) {
        this.myIdentity = userIdentity;
        initListeners();
        favoriteGames = new ArrayList<>();
        placeThumbnails();
        addSocialAspect();
        username.setText(myIdentity.getName());
    }

    private void initListeners() {
        ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                ratingVal = new_val;
                ratingText.setText(String.format("%.2f", ratingVal));
            }
        });
    }

    private void addSocialAspect() {
        for(String s:myIdentity.getFriends()){
            Label friendName = new Label(s);
            friendName.getStyleClass().add(GC_SOCIAL_LABEL_STYLE);
            friendPane.getChildren().add(friendName);
        }
    }

    private void placeThumbnails() {
        try {
            gameData = DataParser.parseConfig("data/player_data.json");
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred when reading in thumbnails");
        }
        // this sets favoriteGames int list
        for (int i = 0; i < gameData.size(); i ++) {
            DataStruct game = gameData.get(i);
            if (!favoriteGames.contains(i) && game.getFavorite()) {
                favoriteGames.add(i);
            } else if (favoriteGames.contains(i) && !game.getFavorite()) {
                favoriteGames.remove(i);
            }
        }
        // make labels
        int favCounter = favoriteGames.size();
        Label favLabel = new Label("Favorites (" + favCounter + ")");
        int gameCounter = gameData.size();
        Label gameLabel = new Label("All Games (" + gameCounter + ")");
        // TODO: set style of labels
        // place thumbnails of each favorite in favoriteGames
        thumbPaneContent.getChildren().add(favLabel);
        for (int fav : favoriteGames) {
            final int index = fav;
            DataStruct game = gameData.get(fav);
            var thumbnailView = new Thumbnail(new Image(this.getClass().getResourceAsStream(game.getImagePath())), game.getName()).getView();
            thumbPaneContent.getChildren().add(thumbnailView);
            thumbnailView.setOnMouseClicked(e -> thumbnailClicked(index));
        }
        // place thumbnails of every game
        thumbPaneContent.getChildren().add(gameLabel);
        int counter = 0;
        for (var game : gameData) {
            final int index = counter;
            var thumbnailView = new Thumbnail(new Image(this.getClass().getResourceAsStream(game.getImagePath())), game.getName()).getView();
            thumbPaneContent.getChildren().add(thumbnailView);
            counter ++;
            thumbnailView.setOnMouseClicked(e -> thumbnailClicked(index));
        }
    }

    private void setFavoriteImage(boolean favorite) {
        ImageView heart;
        if (favorite) heart = new ImageView(new Image(this.getClass().getResourceAsStream("/icons/heartFill.png")));
        else heart = new ImageView(new Image(this.getClass().getResourceAsStream("/icons/heartOutline.png")));
        heart.setFitHeight(40);
        heart.setFitWidth(40);
        favoriteButton.setGraphic(heart);
    }

    public void editFavorites(int gameInt) {
        if (favoriteGames.contains(gameInt)) {
            favoriteGames.remove(gameInt);
        } else {
            favoriteGames.add(gameInt);
        }
    }

    @FXML
    private void favoriteGame() {
        if (gameData.get(myIndex).getFavorite()) {
            gameData.get(myIndex).setFavorite(false, myIndex);
            setFavoriteImage(false);
        }
        else {
            gameData.get(myIndex).setFavorite(true, myIndex);
            setFavoriteImage(true);
        }
    }

    private void thumbnailClicked(int index) {
        this.myIndex = index;

        if (activeThumbnail == myIndex) {
            activeThumbnail = -1;
            titleText.setText("Game Center");
            revertDescription();
            return; // we do not want to call loadGameDetails(), so we return if condition for this if statement is true
        }
        else {
            titleText.setText(gameData.get(myIndex).getName());
            activeThumbnail = myIndex;
        }

        loadGameDetails();
    }

    private void revertDescription() {
        newGamePane.getChildren().remove(activeGameImageView);
        descriptionPane.setVisible(false);
        ratingPane.setVisible(false);
        favoritePane.setVisible(false);
    }

    private void loadGameDetails() {
        if (activeGameImageView != null) {
            newGamePane.getChildren().remove(activeGameImageView);
        }
        loadGameImage();
        loadGameText();
        loadGameFavorite();
        descriptionPane.setVisible(true);
        ratingPane.setVisible(false);
        Label[] scores = new Label[]{score1, score2, score3};
        for(int k = 0; k < NUM_HIGH_SCORES; k++){
            try {
                scores[k].setText(myIdentity.getHighScores(gameData.get(myIndex).getName()).get(k));
            }
            catch(Exception e){
                scores[k].setText(GC_SCORE_EXCEPTIONS);
            }
        }
    }

    private void loadGameImage() {
        activeGameImageView = new ImageView(this.getClass().getResource(gameData.get(myIndex).getImagePath()).toString());
        activeGameImageView.setFitWidth(newGamePane.getWidth());
        activeGameImageView.setFitHeight(newGamePane.getHeight());
        activeGameImageView.setEffect(new GaussianBlur(100));
        var clipRect = new Rectangle();
        clipRect.setWidth(newGamePane.getWidth());
        clipRect.setHeight(newGamePane.getHeight());
        clipRect.setArcWidth(25); clipRect.setArcHeight(25);
        activeGameImageView.setClip(clipRect);
        newGamePane.getChildren().add(activeGameImageView);
    }

    private void loadGameText() {
        descriptionText.setText(gameData.get(myIndex).getDescription());
    }

    private void updateIdentity(UserIdentity userIdentity, String gameName) {
        username.setText(userIdentity.getName());
        for (String s : userIdentity.getFriends()) {
            Label friendName = new Label(s);
            friendName.getStyleClass().add("socialScoreLabel");
            friendPane.getChildren().add(friendName);
        }
        score1.setText(userIdentity.getHighScores(gameName).get(0));
        score2.setText(userIdentity.getHighScores(gameName).get(1));
        score3.setText(userIdentity.getHighScores(gameName).get(2));
    }

    private void loadGameFavorite(){
        favoritePane.setVisible(true);
        setFavoriteImage(gameData.get(myIndex).getFavorite());
    }

    @FXML
    private void launchAuthEnv() {
        new RunAuth().start(new Stage());
    }

    @FXML
    public void launchPlayer() {
        new PlayerStage(this).load(gameData.get(myIndex).getSourcePath(), true);
    }

    @FXML
    private void rateGame() {
        ratingPane.setVisible(true);
        ratingText.setText(String.valueOf(gameData.get(myIndex).getRating()));
        ratingSlider.setValue(gameData.get(myIndex).getRating());
        descriptionPane.setVisible(false);
    }

    @FXML
    private void returnToDescription() {
        ratingPane.setVisible(false);
        descriptionPane.setVisible(true);
        gameData.get(myIndex).setRating(ratingVal.doubleValue(), myIndex);
    }

    public void setHighScore(String gameID, String highScore) {
        if(!myIdentity.getName().equals("")){
            String scoreString = "http://tmtp-spec.appspot.com/newHighScore?username=" + myIdentity.getUsername() +
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
}