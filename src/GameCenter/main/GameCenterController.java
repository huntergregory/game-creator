package GameCenter.main;

import GameCenter.gameData.DataParser;
import GameCenter.gameData.DataStruct;
import GameCenter.utilities.Thumbnail;
import Player.PlayerMain.PlayerStage;
import auth.RunAuth;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network_account.IdentityManager;
import network_account.UserIdentity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Controller for the GameCenter. Works in conjunction with GameCenter.java and GameCenter.fxml, which can be found
 * under the resources folder.
 *
 * This controller defines all actions that occur when a user interacts with the GUI. It also defines several parts of
 * the GUI that cannot be done in fxml, such as placing images parsed from a .json file.
 */
public class GameCenterController {

    private static final String COMMENT_FILE = "res/comments.csv";

    private List<DataStruct> gameData;
    private ArrayList<Integer> favoriteGames;
    private int activeThumbnail;
    private int myIndex;
    private Number ratingVal;
    private ImageView activeGameImageView;
    private ArrayList<Comment> myComments;
    private File commFile;

    @FXML
    public Pane socialPane, newGamePane, descriptionPane, ratingPane, commentPane;
    public Pane favoritePane;
    public TableView<Comment> commentTable;
    public TableColumn<Comment, String> userColumn, commentColumn;
    public ScrollPane thumbPane;
    public GridPane friendPane;
    public Slider ratingSlider;
    public VBox thumbPaneContent;
    public Text titleText, descriptionText, commentText, ratingText, username;
    public TextArea commentBox;
    public Button newGameButton, playButton, editButton, rateButton, commentButton,
            commentEnterButton, ratingConfirmButton, returnButtonR, returnButtonC, favoriteButton;
    public Label score1, score2, score3;
    public Label favLabel, gameLabel;

    void initGameCenter() {
        initListeners();
        favoriteGames = new ArrayList<>();
        myComments = new ArrayList<>();
        commFile = new File(COMMENT_FILE);
        placeThumbnails();
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

    private void placeThumbnails() {
        thumbPaneContent.getChildren().removeAll(thumbPaneContent.getChildren());
        try {
            gameData = DataParser.parseConfig("data/player_data.json");
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred when reading in thumbnails");
        }
        favoriteGames = new ArrayList<>();
        for (int i = 0; i < gameData.size(); i ++) {
            DataStruct game = gameData.get(i);
            if (game.getFavorite()) {
                favoriteGames.add(i);
            }
        }
        int favCounter = favoriteGames.size();
        favLabel.setText("Favorites (" + favCounter + ")");
        int gameCounter = gameData.size();
        gameLabel.setText("All Games (" + gameCounter + ")");
        thumbPaneContent.getChildren().add(favLabel);
        for (int fav : favoriteGames) {
            final int index = fav;
            DataStruct game = gameData.get(fav);
            var thumbnailView = new Thumbnail(new Image(this.getClass().getResourceAsStream(game.getImagePath())), game.getName()).getView();
            thumbPaneContent.getChildren().add(thumbnailView);
            thumbnailView.setOnMouseClicked(e -> thumbnailClicked(index));
        }
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
        heart.setFitHeight(33);
        heart.setFitWidth(33);
        favoriteButton.setGraphic(heart);
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
        placeThumbnails();
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

    private void buildCommentTable() {
        myComments = new ArrayList<>();
        try {
        Scanner sc = new Scanner(new FileReader(commFile));
        while (sc.hasNextLine()) {
            myComments.add(parseComment(sc.nextLine()));
        }
        } catch (IOException e) {
            System.out.println("Error occurred when reading comments.");
        }
        commentTable.getItems().clear();
        for (Comment com : myComments) {
            if (com.getMyGame() == myIndex) {
                commentTable.getItems().add(com);
            }
        }
    }

    private Comment parseComment(String line) {
        String[] lineSplit = line.split(",");
        String game = lineSplit[0];
        String user = lineSplit[1];
        String lineSplit2 = "";
        for (int i = 2; i < lineSplit.length; i++) {
            lineSplit2 += lineSplit[i];
        }
        Comment c = new Comment(Integer.parseInt(game), user, lineSplit2);
        return c;
    }

    private String reduceComment(Comment com) {
        String game = Integer.toString(com.getMyGame());
        String user = com.getMyUser();
        String comm = com.getMyComment();
        return game + "," + user + "," + comm;
    }

    @FXML
    private void launchAuthEnv() {
        new RunAuth().start(new Stage());
    }

    @FXML
    private void launchPlayer() {
        new PlayerStage().run(gameData.get(myIndex).getSourcePath());
    }

    @FXML
    private void comment() {
        commentPane.setVisible(true);
        buildCommentTable();
        descriptionPane.setVisible(false);
    }

    @FXML
    private void enterComment() {
        String commInput = commentBox.getText();
        commentBox.clear();
        Comment c = new Comment(myIndex, username.getText(), commInput);
        myComments.add(c);

        try {
            FileWriter fw = new FileWriter(commFile);
            String cString = "";
            for (var comm : myComments) {
                cString += reduceComment(comm);
                cString += '\n';
            }
            cString = cString.substring(0, cString.length() - 1);
            fw.write(cString);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing comment.");
        }

        buildCommentTable();
    }

    @FXML
    private void rateGame() {
        ratingPane.setVisible(true);
        ratingText.setText(String.valueOf(gameData.get(myIndex).getRating()));
        ratingSlider.setValue(gameData.get(myIndex).getRating());
        descriptionPane.setVisible(false);
    }

    @FXML
    private void setRating() {
        gameData.get(myIndex).setRating(ratingVal.doubleValue(), myIndex);
    }

    @FXML
    private void returnToDescription() {
        ratingPane.setVisible(false);
        commentPane.setVisible(false);
        descriptionPane.setVisible(true);
    }

    public void setHighScore(IdentityManager IM, String gameID, String highScore) {
        IM.addHighScore(gameID, highScore);
    }
}