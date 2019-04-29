package Player.PlayerMain;

import Engine.src.Controller.GameController;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Components.MotionComponent;
import Engine.src.Controller.LevelController;
import gamedata.Game;
import gamedata.GameObjects.Instance;
import gamedata.serialization.Serializer;
import hud.DataTracker;
import hud.HUDView;
import hud.NumericalDataTracker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PlayerStage {
    private final String STYLESHEET = "style.css";
    private final double HUD_WIDTH = 300;

    public final String ST_TITLE = "Cracking Open a Scrolled One with the Boys";
    public final double ST_WIDTH = 800;
    public final double ST_HEIGHT = 600;
    public final Paint ST_COLOR = Color.web("284376");

    public final double STEP_TIME = 5;
    public final double GAME_WIDTH = 1400;
    public final double GAME_HEIGHT = 800;
    public final Paint GAME_BG = Color.BLACK;

    public static final int FRAMES_PER_SECOND = 15;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int HUD_UPDATE_DELAY = 10;
    private static final boolean HUD_INCLUDES_PLOTTER = true;

    private Scene myScene;
    private GridPane myVisualRoot;
    private BorderPane myBorderPane;
    private HUDView myHud;

    private LevelController myLevelController;
    private GameController myGameController;
    private Pane myGameRoot;
    private Set<Instance> myInstances;
    private Map<Instance, ImageView> myImageViewMap;
    private int myLevelNumber;

    private NumericalDataTracker<Double> myXPosTracker;
    private NumericalDataTracker<Double> myYPosTracker;
    private NumericalDataTracker<Double> myYVelocity;
    private NumericalDataTracker<Double> myTimeTracker;
    private NumericalDataTracker<Integer> myLivesTracker;
    private NumericalDataTracker<Integer> myScoreTracker;
    private DataTracker<String> myPowerupTracker;

    private int myCount;
    private int gamePaused;

    public PlayerStage() {
        myVisualRoot = new GridPane();
        //mySidePanelWidth = ST_WIDTH / 3.0;
        //myLeftPanel = new SidePanel(mySidePanelWidth);
        //myBorderPane = new BorderPane();
        //myBorderPane.setLeft(myLeftPanel.getPane());
        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
        //myScene = new Scene(myBorderPane, ST_WIDTH, SCREEN_HEIGHT, ST_COLOR);
        myScene.getStylesheets().add(STYLESHEET);
        try {
            new Serializer().serialize(List.of("addCollision('Mario', 'Block', 'script');", "addCollision('Mario', 'Turtle');"));
        }
        catch (IOException e) {
            System.out.println("Couldn't write to file.");
        }
    }

    public void run(Game game, Boolean debug) {
        if (debug) {
            // TODO: make method, adding console for debug mode
        }
    }

    public void load(String gameName) {
        try {
            Serializer serializer = new Serializer();
            GameLoader loader = new GameLoader(0, serializer.deserialize(Serializer.FILE_PATH));
            myGameController = new GameController(MILLISECOND_DELAY, ST_WIDTH, ST_HEIGHT, GAME_WIDTH, GAME_HEIGHT, loader.getGameLogic());
            myLevelNumber = loader.getMyLevelNumber();
            startNewLevel();
        } catch (IOException e) {
            System.out.println("Loading " + gameName + " did not work.");
        }
    }

    private void startNewLevel() {
        myLevelController = myGameController.getLevelController(myLevelNumber);
        Stage gameStage = new Stage();
        myInstances = myLevelController.getEntities();

        initDataTrackers();
        initBorderPane();

        addNewImageViews();

        Scene gameScene = new Scene(myBorderPane, GAME_BG);
        //gameScene.getStylesheets().add("style.css");
        gameScene.getStylesheets().add("hud.css");
        gameStage.setScene(gameScene);
        gameStage.show();
        gameScene.setOnKeyPressed(e -> myLevelController.processKey(e.getCode().toString()));
        animate();
    }

    private void setHud() {
        myHud = new HUDView(HUD_WIDTH, ST_HEIGHT, "GameLoader 1", HUD_INCLUDES_PLOTTER, myXPosTracker,
                myYPosTracker,
                myYVelocity,
                myTimeTracker,
                myLivesTracker,
                myPowerupTracker);
    }

    private void initBorderPane() {
        myBorderPane = new BorderPane();
        myGameRoot = new Pane();
        myBorderPane.setCenter(myGameRoot);
        setHud();
        myBorderPane.setLeft(myHud.getNode());
    }

    private void animate() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        setGamePaused();
        if (gamePaused == 0) {
            if (myLevelController.levelPassed()) {
                myLevelNumber++;
                startNewLevel();
            }
            myLevelController.updateScene();
            addNewImageViews();
            updateOrRemoveImageViews();

            if (myCount % HUD_UPDATE_DELAY == 0) {
                updateDataTrackers();
                myHud.update();
            }
            myCount++;
        }
    }

    private void updateOrRemoveImageViews() {
        for (Instance instance : myImageViewMap.keySet()) {
            if (myInstances.contains(instance))
                myGameRoot.getChildren().remove(myImageViewMap.get(instance));
            updateImageView(instance);
        }
    }

    private void addNewImageViews() {
        for (Instance instance : myInstances) {
            if (myImageViewMap.containsKey(instance))
                continue;
            var newImageView = new ImageView();
            myImageViewMap.put(instance, newImageView);
            myGameRoot.getChildren().add(newImageView);
            updateImageView(instance);
        }
    }

    private void updateImageView(Instance instance) {
        BasicComponent basicComponent = instance.getComponent(BasicComponent.class);
        MotionComponent motionComponent = instance.getComponent(MotionComponent.class);
        HealthComponent healthComponent = instance.getComponent(HealthComponent.class);
        if (basicComponent == null)
            return;

        ImageView imageView = myImageViewMap.get(instance.getID());
        moveAndResize(imageView, basicComponent);
        setImageIfNecessary(imageView, basicComponent);
    }

    private void moveAndResize(ImageView imageView, BasicComponent basicComponent) {
        imageView.setX(basicComponent.getX() - myLevelController.getOffset()[0]);
        imageView.setY(basicComponent.getY() - myLevelController.getOffset()[1]);
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

    private void initDataTrackers() {
        myXPosTracker = new NumericalDataTracker<>("X Position");
        myYPosTracker = new NumericalDataTracker<>("Y Position");
        myTimeTracker = new NumericalDataTracker<>("Time");
        myYVelocity = new NumericalDataTracker<>("Y Velocity");
        myLivesTracker = new NumericalDataTracker<>("Lives");
        myScoreTracker = new NumericalDataTracker<>("Score");
        myPowerupTracker = new DataTracker<>("Powerup");
    }

    private void updateDataTrackers() {
        Instance userInstance = myLevelController.getUserInstance();
        BasicComponent basicComponent = userInstance.getComponent(BasicComponent.class);
        MotionComponent motionComponent = userInstance.getComponent(MotionComponent.class);
        myTimeTracker.storeData(myCount * 1.0); //TODO get actual time
        myXPosTracker.storeData(basicComponent.getX());
        myYPosTracker.storeData(basicComponent.getY());
        myYVelocity.storeData(motionComponent.getYVelocity());
        myLivesTracker.storeData(2); //FIXME
        myScoreTracker.storeData(0); //FIXME
        myPowerupTracker.storeData("Flower"); //FIXME
    }

    /**
     * edit(), rate() currently placeholder. Update these methods.
     */
    public void edit(String gameName) {
//        System.out.println(gameName + " is being edited!");
    }

    public void rate(String gameName) {
//        System.out.println("Rating for " + gameName + " is being changed!");
    }

    public Stage makeStage() {
        Stage ret = new Stage();
        ret.setTitle(ST_TITLE);
        ret.setScene(myScene);
        return ret;
    }

    private void setGamePaused() {
        gamePaused = myHud.getGamePaused();
    }

    public int getGamePaused() {
        return gamePaused;
    }

}