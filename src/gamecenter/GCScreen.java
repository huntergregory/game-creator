package gamecenter;

import Player.src.PlayerMain.PlayerStage;
import auth.RunAuth;
import demotests.RunDemo;
import gamecenter.gamedata.DataParser;
import gamecenter.gamedata.DataStruct;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uiutils.components.ButtonGenerator;
import uiutils.panes.CentrePane;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static gamecenter.Colours.*;
import static gamecenter.Dimensions.*;
import static gamecenter.RunGameCenter.*;
import static gamecenter.Strings.*;
import static gamecenter.Styles.*;
import static auth.Styles.CENTRE_PANE_STYLE;

public class GCScreen {
    private Stage stage;
    private Scene scene;
    private Pane parent;
    private List<DataStruct> gameData;
    private uiutils.panes.Pane titlePane, permaPane, gameListPane;
    private int activeThumbnail = -1;
    private List<Thumbnail> thumbnails;
    private Text defaultPermaText;
    private Group button;
    private Text titleText;

    public GCScreen() {
        thumbnails = new ArrayList<>();
        stage = initStage();
        placePanes();
        placeTexts();
        placeThumbnails();
    }

    private void placeThumbnails() {
        try {
            gameData = DataParser.parseConfig("data/player_data.json");
            int i = 0;
            var sp = new ScrollPane();
            sp.setMinWidth(THUMBNAIL_SIZE + 40);
            sp.setPrefWidth(THUMBNAIL_SIZE + 40);
            sp.setLayoutX(GC_RIGHT_PANE_WIDTH/2 - THUMBNAIL_SIZE/2 - 20);
            sp.setPrefHeight(GC_RIGHT_PANE_HEIGHT);
            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            var scrollContents = new VBox(20);
            sp.setContent(scrollContents);
            sp.setStyle("-fx-background: #333333;\n" +
                    "   -fx-border-color: transparent;" +
                    "-fx-background-color: #33333300;");
            for(var d : gameData) {
                var thumbnail = new Thumbnail(new Image(GCScreen.class.getResourceAsStream("/img/"+d.imagePath)));
                thumbnails.add(thumbnail);
                scrollContents.getChildren().add(thumbnail.getView());
                final int index = i;
                thumbnail.getView().setOnMouseClicked(e -> thumbnailClicked(index));
                if (i == 0) {
                    VBox.setMargin(thumbnail.getView(), new Insets(20, 0, 0, 17.5));
                } else if (i == gameData.size()-1) {
                    VBox.setMargin(thumbnail.getView(), new Insets(0, 0, 20, 17.5));
                } else {
                    VBox.setMargin(thumbnail.getView(), new Insets(0, 0, 0, 17.5));
                }
                i ++;
            }
            gameListPane.getView().getChildren().add(sp);
        } catch (FileNotFoundException e) {
            // Shouldn't ever happen
        }
    }

    private void thumbnailClicked(int index) {
        if (activeThumbnail == index) {
            thumbnails.get(activeThumbnail).deselect();
            activeThumbnail = -1;
            resetPermaPane();
            return;
        }
        if (activeThumbnail != -1) {
            thumbnails.get(activeThumbnail).deselect();
        }

        thumbnails.get(index).select();
        activeThumbnail = index;
        loadGameDetails(index);
    }

    private ImageView permaBgImage; private HBox actionsContainer;
    private void loadGameDetails(int index) {
        if (permaBgImage != null) {
            permaPane.getView().getChildren().remove(permaBgImage);
        }
        button.setVisible(false);
        defaultPermaText.setVisible(false);
        titleText.setText(gameData.get(index).name);
        adjustTextPaneWidth();

        stylePerma(index);
        loadPermaTextDetails(index);
        prepareActionButtons(index);
    }

    private void prepareActionButtons(int index) {
        actionsContainer.setVisible(true);
        actionsContainer.getChildren().get(0).setOnMouseClicked(e -> editClickListener(index));
        actionsContainer.getChildren().get(1).setOnMouseClicked(e -> playClickListener(index));
        actionsContainer.getChildren().get(2).setOnMouseClicked(e -> socialClickListener(index));
    }

    private void editClickListener(int index) {
        // TODO: Open auth env for this game
    }

    private void playClickListener(int index) {
        // TODO: Open player for this game
        if (index == 0) {
            new RunDemo().run();
        } else if (index == 3) {
            new PlayerStage().run("mario");  //COMMENT to show player and run mario from there
            //new PlayerStage().makeStage().show();    UNCOMMENT to show player
        }
    }

    private void socialClickListener(int index) {
        // TODO: Open social centre for this game
    }

    private void loadPermaTextDetails(int index) {
        gameDesc.setText(gameData.get(index).desc);

        double gameDescX = GC_DESC_TEXT_X - gameDesc.getBoundsInLocal().getWidth()/2;
        double gameDescY = GC_DESC_TEXT_Y - gameDesc.getBoundsInLocal().getHeight()/2;
        gameDesc.setX(gameDescX);
        gameDesc.setY(gameDescY);
        if (gameDescPane != null && parent.getChildren().contains(gameDescPane.getView()))
            parent.getChildren().remove(gameDescPane.getView());
        if (gameDesc != null && parent.getChildren().contains(gameDesc))
            parent.getChildren().remove(gameDesc);
        gameDescPane = new CentrePane(gameDescX - 15, gameDescY - 15 - 7.5, gameDesc.getBoundsInLocal().getWidth() + 30,
                gameDesc.getBoundsInLocal().getHeight() + 30 - 7.5);
        gameDesc.setVisible(true); gameDescPane.getView().setVisible(true);
        parent.getChildren().addAll(gameDescPane.getView(), gameDesc);
    }

    private void stylePerma(int index) {

        var imgPath = GCScreen.class.getResource("/img/"+gameData.get(index).imagePath).toString();
        permaBgImage = new ImageView(new Image(imgPath));
        permaBgImage.setFitWidth(permaPane.getView().getWidth());
        permaBgImage.setFitHeight(permaPane.getView().getHeight());
        var clipRect = new Rectangle();
        clipRect.setWidth(permaPane.getView().getWidth());
        clipRect.setHeight(permaPane.getView().getHeight());
        clipRect.setArcWidth(25); clipRect.setArcHeight(25);
        permaBgImage.setClip(clipRect);
        permaBgImage.setEffect(new GaussianBlur(100));
        permaPane.getView().setStyle("-fx-background-color: transparent; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
        permaPane.getView().getChildren().add(permaBgImage);
    }

    private void resetPermaPane() {
        button.setVisible(true);
        defaultPermaText.setVisible(true);
        titleText.setText(GC_WINDOW_TITLE);
        adjustTextPaneWidth();
        permaPane.getView().setStyle(CENTRE_PANE_STYLE);
        gameDesc.setVisible(false); gameDescPane.getView().setVisible(false);
        if (permaBgImage != null) {
            permaPane.getView().getChildren().remove(permaBgImage);
        }
        actionsContainer.setVisible(false);
    }

    private void adjustTextPaneWidth() {
        titlePane.getView().setMinWidth(titleText.getBoundsInLocal().getWidth() + 18.5*2);
        titlePane.getView().setPrefWidth(titleText.getBoundsInLocal().getWidth() + 18.5*2);
        titlePane.getView().setMaxWidth(titleText.getBoundsInLocal().getWidth() + 18.5*2);
    }

    private void placeTexts() {
        titleText = new Text(GC_WINDOW_TITLE);
        titleText.setFont(bebasKaiMedium); titleText.setX(GC_TITLE_TEXT_X); titleText.setY(GC_TITLE_TEXT_Y);
        titleText.setFill(TEXT_COLOUR_PRIMARY);
        placeDefaultPermaContent();
        placeAltPermaContent();

        parent.getChildren().addAll(titleText);
        adjustTextPaneWidth();
    }

    private Text gameDesc;
    private CentrePane gameDescPane;
    private Group playButton, editButton, socialButton;

    private void placeAltPermaContent() {
        gameDesc = new Text();
        gameDesc.setFont(sofiaProSmall);
        gameDesc.setFill(TEXT_COLOUR_PRIMARY);
        gameDesc.setVisible(false);
        gameDesc.setWrappingWidth(200);

        placeHiddenPermaActionButtons();
    }

    private void placeHiddenPermaActionButtons () {
        actionsContainer = new HBox();
        editButton = ButtonGenerator.createButton(EDIT_GAME_BUTTON_TEXT, true);
        playButton = ButtonGenerator.createButton(PLAY_GAME_BUTTON_TEXT, true);
        socialButton = ButtonGenerator.createButton(SOCIAL_BUTTON_TEXT, true);
        actionsContainer.getChildren().addAll(editButton, playButton, socialButton);
        actionsContainer.setLayoutX(GC_TITLE_PANE_X +
                GC_PERMA_PANE_WIDTH/2-
                (actionsContainer.getBoundsInLocal().getWidth()*3 + actionsContainer.getSpacing()*2)/2);
        actionsContainer.setLayoutY(GC_PERMA_PANE_Y + GC_PERMA_PANE_HEIGHT-actionsContainer.getBoundsInLocal().getHeight()-ACTIONS_MARGIN);
        actionsContainer.setVisible(false);
        parent.getChildren().add(actionsContainer);
    }

    private void placeDefaultPermaContent() {
        defaultPermaText = new Text(GC_DEFAULT_PERMA_TEXT);
        defaultPermaText.setFont(sofiaProSmall);
        defaultPermaText.setFill(TEXT_COLOUR_PRIMARY);

        defaultPermaText.setX(GC_TITLE_PANE_X + GC_PERMA_PANE_WIDTH/2 - defaultPermaText.getBoundsInLocal().getWidth()/2);
        defaultPermaText.setY(GC_PERMA_PANE_Y + GC_PERMA_PANE_HEIGHT/2 - defaultPermaText.getBoundsInLocal().getHeight()/2
        - GC_PERMA_CONTENT_OFFSET);

        button = ButtonGenerator.createButton(CREATE_GAME_BUTTON_TEXT);
        button.setLayoutX(GC_TITLE_PANE_X + GC_PERMA_PANE_WIDTH/2 - button.getLayoutBounds().getWidth()/2 + BUTTON_OFFSET_CORRECTION);
        button.setLayoutY(GC_PERMA_PANE_Y + GC_PERMA_PANE_HEIGHT/2 - button.getLayoutBounds().getHeight()/2
        + GC_PERMA_CONTENT_OFFSET);
        button.setOnMouseClicked(e -> {new RunAuth().start(new Stage());});

        parent.getChildren().addAll(defaultPermaText, button);
    }

    private void placePanes() {
        gameListPane = new CentrePane(GC_RIGHT_PANE_X, GC_RIGHT_PANE_Y, GC_RIGHT_PANE_WIDTH, GC_RIGHT_PANE_HEIGHT);
        titlePane = new CentrePane(GC_TITLE_PANE_X, GC_TITLE_PANE_Y, GC_TITLE_PANE_WIDTH, GC_TITLE_PANE_HEIGHT);
        permaPane = new CentrePane(GC_TITLE_PANE_X, GC_PERMA_PANE_Y, GC_PERMA_PANE_WIDTH, GC_PERMA_PANE_HEIGHT);

        parent.getChildren().addAll(gameListPane.getView(), titlePane.getView(), permaPane.getView());
    }

    private Stage initStage() {
        var stage = new Stage();
        stage.setTitle(GC_WINDOW_TITLE);
        stage.setScene(scene = initScene());
        stage.setResizable(false);

        return stage;
    }

    private Scene initScene() {
        parent = new Pane();
        parent.setStyle(GC_BG_STYLE);

        parent.setPrefWidth(GC_WINDOW_WIDTH);
        parent.setPrefHeight(GC_WINDOW_HEIGHT);

        return new Scene(parent);

    }

    public void show() {
        stage.show();
    }
}
