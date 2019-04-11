package PlayerMain;

import Components.BasicComponent;
import Components.Component;
import Controller.Controller;
import Regions.DescriptionRegion;
import Regions.GamesRegion;
import Regions.Thumbnail;
import Regions.TitleRegion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerStage {
    private final String STYLESHEET = "style.css";
    private final String GRIDPANE_STYLESHEET = "gridPane";
    private final String GAMES_STYLESHEET = "gamesRegion";
    private final String DESC_STYLESHEET = "descRegion";
    private final String TITLE_STYLESHEET = "titleRegion";

    public final String ST_TITLE = "Cracking Open a Scrolled One with the Boys";
    public final double ST_WIDTH = 8000;
    public final double ST_HEIGHT = 600;
    public final Paint ST_COLOR = Color.web("284376");
    public final double ST_SPACING = 20;

    public final double STEP_TIME = 5;
    public final double GAME_WIDTH = 1400;
    public final double GAME_HEIGHT = 800;
    public final Paint GAME_BG = Color.BLACK;

    public static final int FRAMES_PER_SECOND = 15;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;
    private GridPane myVisualRoot;
    private ArrayList<String> myGames;
    private ArrayList<String> myImageFiles;

    private Controller myGameController;
    private Group myGameRoot;
    private Map<Integer, Map<Class<? extends Component>, Component>> myGameEntityMap;
    private Map<Integer, ImageView> myImageViewMap;

    public PlayerStage() {

        myVisualRoot = buildRoot();
        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
        myScene.getStylesheets().add(STYLESHEET);

    }

    public GridPane buildRoot() {

        GridPane base = new GridPane();
        base.getStyleClass().add(GRIDPANE_STYLESHEET);
        base.setVgap(ST_SPACING);
        base.setHgap(ST_SPACING);

        GamesRegion gamesRegion = new GamesRegion(GAMES_STYLESHEET);
        DescriptionRegion descRegion = new DescriptionRegion(DESC_STYLESHEET, this);
        TitleRegion titleRegion = new TitleRegion(TITLE_STYLESHEET);

        ScrollPane gamesPane = gamesRegion.getPane();
        setLambdas(gamesRegion.getThumbnails(), descRegion);
        ScrollPane descPane = descRegion.getPane();
        GridPane titlePane = titleRegion.getPane();

        base.add(titlePane, 0, 0, 1, 1);
        base.add(descPane, 0, 1, 1, 1);
        base.add(gamesPane, 1, 0, 1, 2);

        return base;
    }

    private void setLambdas(ArrayList<Thumbnail> thumbnails, DescriptionRegion descRegion) {
        for (Thumbnail thumb : thumbnails) {
            StackPane thumbPane = thumb.getPane();
            thumbPane.setOnMouseClicked(e -> descRegion.updateRegion(thumb.getName()));
        }
    }

    public void run(String gameName) {
        Stage gameStage = new Stage();
        myGameRoot = new Group();
        myImageViewMap = new HashMap<>();                   //FIXME go full screen
        myGameController = new Controller(STEP_TIME, myScene.getWidth(), myScene.getHeight(), GAME_WIDTH, GAME_HEIGHT);
        myGameEntityMap = myGameController.getEntities();

        addNewImageViews();

        Scene gameScene = new Scene(myGameRoot, GAME_BG);
        gameStage.setScene(gameScene);
        gameStage.show();

        gameScene.setOnKeyPressed(e -> myGameController.processKey(e.getCode().toString()));

        animate();
    }

    private void animate() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        myGameController.updateScene();
        addNewImageViews();
        updateOrRemoveImageViews();
    }

    private void updateOrRemoveImageViews() {
        for (int id : myImageViewMap.keySet()) {
            if (!myGameEntityMap.containsKey(id))
                myGameRoot.getChildren().remove(myImageViewMap.get(id));
            updateImageView(id);
        }
    }

    private void addNewImageViews() {
        for (int id : myGameEntityMap.keySet()) {
            if (myImageViewMap.containsKey(id))
                continue;

            var newImageView = new ImageView();
            myImageViewMap.put(id, newImageView);
            myGameRoot.getChildren().add(newImageView);
            updateImageView(id);
        }
    }

    private void updateImageView(int id) {
        BasicComponent basicComponent = (BasicComponent) myGameEntityMap.get(id).get(BasicComponent.class);
        if (basicComponent == null)
            return;

        ImageView imageView = myImageViewMap.get(id);
        moveAndResize(imageView, basicComponent);
        setImageIfNecessary(imageView, basicComponent);
    }

    private void moveAndResize(ImageView imageView, BasicComponent basicComponent) {
        imageView.setX(basicComponent.getX() - myGameController.getOffset()[0]);
        imageView.setY(basicComponent.getY() - myGameController.getOffset()[1]);
        imageView.setFitWidth(basicComponent.getWidth());
        imageView.setFitHeight(basicComponent.getHeight());
    }

    private void setImageIfNecessary(ImageView imageView, BasicComponent entity) {
        InputStream newInputStream = this.getClass().getResourceAsStream(entity.getMyFilename());
        if (newInputStream == null)
            return;

        Image newImage = new Image(newInputStream);
        if (!newImage.equals(imageView.getImage()))
            imageView.setImage(newImage);
    }

    /**
     *    edit(), rate() currently placeholder. Update these methods.
     */
    public void edit(String gameName) {
//        System.out.println(gameName + " is being edited!");
    }

    public void rate(String gameName) {
//        System.out.println("Rating for " + gameName + " is being changed!");
    }

    protected Stage makeStage() {
        Stage ret = new Stage();
        ret.setTitle(ST_TITLE);
        ret.setScene(myScene);
        myScene.getStylesheets().add("style.css");
        return ret;
    }
}