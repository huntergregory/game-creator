package Player.PlayerMain;

import Engine.src.Controller.GameController;
import Engine.src.EngineData.Components.*;
import Player.Features.PlayerButtons;
import Player.Features.SidePanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import GameCenter.main.GameCenterController;
import Engine.src.EngineData.EngineInstance;
import Player.Features.DebugConsole;
import Engine.src.Controller.LevelController;
import gamedata.Game;
import gamedata.Resource;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.*;

public class PlayerStage {
    private static final double OFFSET_THRESHOLD = 200;
    private final String STYLESHEET = "style.css";
    private final double HUD_WIDTH = 300;
    private final double ST_WIDTH = 800;
    private final double ST_HEIGHT = 600;
    private final Paint ST_COLOR = Color.web("284376");
    private final double STEP_TIME = 5;
    private final Paint GAME_BG = Color.BLACK;
    private static final int FRAMES_PER_SECOND = 15;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int HUD_UPDATE_DELAY = 10;
    private static final boolean HUD_INCLUDES_PLOTTER = true;

    private VBox myVBox;
    private Game myGame;
    private Stage myGameStage;
    private Scene myScene;
    private GridPane myVisualRoot;
    private BorderPane myBorderPane;
    private HUDView myHud;
    private PlayerButtons myPlayerButtons;
    private DebugConsole myDebugConsole;

    private GameCenterController myGameCenterController;
    private LevelController myLevelController;
    private GameController myGameController;
    private Pane myGameRoot;
    private Map<String, EngineInstance> myEngineInstances;
    private Map<EngineInstance, ImageView> myImageViewMap;
    private List<MediaPlayer> mySounds;

    private NumericalDataTracker<Double> myXPosTracker;
    private NumericalDataTracker<Double> myYPosTracker;
    private NumericalDataTracker<Double> myYVelocity;
    private NumericalDataTracker<Double> myTimeTracker;
    private NumericalDataTracker<Integer> myLivesTracker;
    private NumericalDataTracker<Integer> myScoreTracker;

    private EngineInstance userEngineInstance;
    private BasicComponent basicComponent;
    private MotionComponent motionComponent;
    private LivesComponent livesComponent;
    private ScoreComponent scoreComponent;

    private final String FILE_NOT_FOUND = "File not found";
    private int myCount;
    private int gamePaused;
    private Boolean debugMode;
    private int myLevelNumber;
    private String myGameID;

    public PlayerStage(GameCenterController gameCenterController) {
        myGameCenterController = gameCenterController;
        myVisualRoot = new GridPane();
        double mySidePanelWidth = ST_WIDTH / 3.0;
        var myLeftPanel = new SidePanel(mySidePanelWidth);
        myBorderPane = new BorderPane();
        myBorderPane.setLeft(myLeftPanel.getPane());
        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
        myScene.getStylesheets().add(STYLESHEET);
    }

    public void load(String fileName, boolean debug) {
        myGameID = fileName;
        debugMode = debug;
        String contents = new Scanner(fileName).useDelimiter("\\Z").next();
        myGame = new Gson().fromJson(contents, new TypeToken<Game>() {}.getType());
        myGameController = new GameController(STEP_TIME, ST_WIDTH, ST_HEIGHT, myGame);
        myLevelNumber = myGame.currentLevel;
        startNewLevel();
    }

    private void startNewLevel() {
        myLevelController = myGameController.getLevelController();
        myEngineInstances = myLevelController.getEngineInstances();
        myImageViewMap = new HashMap<>();
        initAndRemoveSounds();
        initDataTrackers();
        initBorderPane();
        initializeImageViews();
        initializeBackGround();
        Scene gameScene = new Scene(myBorderPane, GAME_BG);
        gameScene.getStylesheets().add("hud.css");
        myGameStage = new Stage();
        myGameStage.setScene(gameScene);
        myGameStage.show();
        gameScene.setOnKeyPressed(e -> myLevelController.processKey(e.getCode().toString()));
        animate();
    }

    private void initializeBackGround() {
        myGame.scenes.get(myGame.currentLevel);
        myScene.setFill(Color.BLACK);
    }

    private void initializeImageViews() {
        InputStream newInputStream = null;
        for (String inst : myEngineInstances.keySet()) {
            EngineInstance instance = myEngineInstances.get(inst);
            BasicComponent basic = instance.getComponent(BasicComponent.class);
            for (Resource resource : myGame.resources) {
                if (basic.getMyFilename().equals(resource.resourceID)) {
                    newInputStream = this.getClass().getResourceAsStream(resource.src);
                }
            }
            if (newInputStream == null) {
                return;
            }

            Image newImage = new Image(newInputStream);
            ImageView imageView = new ImageView(newImage);
            myImageViewMap.put(instance, imageView);
            moveAndResize(imageView, basic);
            myGameRoot.getChildren().add(imageView);
        }
    }
    private void setHud() {
        myHud = new HUDView(HUD_WIDTH, ST_HEIGHT, "GameLoader 1", HUD_INCLUDES_PLOTTER, myXPosTracker,
                myYPosTracker,
                myYVelocity,
                myTimeTracker,
                myLivesTracker);
    }

    private void setPlayerButtons() {
        myPlayerButtons = new PlayerButtons(this);
    }

    private void setDebugConsole() {
        myDebugConsole = new DebugConsole(HUD_WIDTH, ST_HEIGHT);
    }

    private void initBorderPane() {
        myBorderPane = new BorderPane();
        myGameRoot = new Pane();
        myBorderPane.setCenter(myGameRoot);
        setHud();
        setPlayerButtons();
        myVBox = new VBox();
        myVBox.getChildren().add(myPlayerButtons.getNode());
        if (debugMode) {
            setDebugConsole();
            myDebugConsole.addText("");
            myVBox.getChildren().add(myDebugConsole.getMainComponent());
        }
        myBorderPane.setLeft(myHud.getNode());
        myBorderPane.setRight(myVBox);
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
                myGame.currentLevel = myGame.currentLevel + 1;
                startNewLevel();
            }
            myLevelController.updateScene();
            addNewImageViews();
            updateOrRemoveImageViews();
            updateDebugLog();
            updateSounds();
            if (myCount % HUD_UPDATE_DELAY == 0) {
                updateDataTrackers();
                myHud.update();
            }
            myCount++;
        }
    }

    private void cacheImageViewDisplay(EngineInstance inst) {
        BasicComponent basic = inst.getComponent(BasicComponent.class);
        BasicComponent userBasic = myLevelController.getUserEngineInstance().getComponent(BasicComponent.class);
        boolean outOfVisibleRange = basic.getX() < userBasic.getX() - (ST_WIDTH / 2) - OFFSET_THRESHOLD ||
                basic.getX() > userBasic.getX() + (ST_WIDTH / 2) + OFFSET_THRESHOLD ||
                basic.getY() < userBasic.getY() - (ST_HEIGHT / 2) - OFFSET_THRESHOLD ||
                basic.getY() > userBasic.getY() + (ST_HEIGHT / 2) + OFFSET_THRESHOLD;
        if (outOfVisibleRange) {
            myImageViewMap.remove(inst);
        }
        else if (! outOfVisibleRange || ! myImageViewMap.containsKey(inst)) {
            var newImageView = new ImageView();
            myImageViewMap.put(inst, newImageView);
            myGameRoot.getChildren().add(newImageView);
            updateImageView(inst);
        }
    }

    private void updateDebugLog() {
        if (debugMode) {
            List<String> debugLog = myLevelController.debugLog();
            myDebugConsole.update(debugLog);
        }
    }

    private void updateSounds() {
        Map<String, Boolean> sounds = myLevelController.playSound();
        for (Map.Entry<String, Boolean> sound : sounds.entrySet()) {
            MediaPlayer a = new MediaPlayer(new Media(getClass().getResource(sound.getKey()).toString()));
            if (sound.getValue()) {
                a.setOnEndOfMedia(() -> a.seek(Duration.ZERO));
            }
            else {
                a.setOnEndOfMedia(() -> mySounds.remove(a));
            }
            mySounds.add(a);
            a.play();
        }
    }

    private void initAndRemoveSounds() {
        if (mySounds == null) {
            mySounds = new ArrayList<>();
            return;
        }
        for (MediaPlayer m: mySounds) {
            m.setMute(true);
        }
        mySounds.clear();
    }

    private void updateOrRemoveImageViews() {
        for (EngineInstance engineInstance : myImageViewMap.keySet()) {
            BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
            if (!myEngineInstances.containsKey(engineInstance.getID()) || !basic.isAlive())
                myGameRoot.getChildren().remove(myImageViewMap.get(engineInstance));
            updateImageView(engineInstance);
        }
    }

    private void addNewImageViews() {
        for (String ID : myEngineInstances.keySet()) {
            EngineInstance engineInstance = myEngineInstances.get(ID);
            if (myImageViewMap.containsKey(engineInstance))
                continue;
            cacheImageViewDisplay(engineInstance);
        }
    }

    private void updateImageView(EngineInstance engineInstance) {
        BasicComponent basicComponent = engineInstance.getComponent(BasicComponent.class);
        if (basicComponent == null) {
            return;
        }
        ImageView imageView = myImageViewMap.get(engineInstance);
        if (imageView.getImage() == null) {
            setAnImage(imageView, basicComponent);
        }
        moveAndResize(imageView, basicComponent);
    }

    private void moveAndResize(ImageView imageView, BasicComponent basicComponent) {
        imageView.setX(basicComponent.getX() - myLevelController.getOffset()[0]);
        imageView.setY(basicComponent.getY() - myLevelController.getOffset()[1]);
        imageView.setFitWidth(basicComponent.getWidth());
        imageView.setFitHeight(basicComponent.getHeight());
    }

    private void setAnImage(ImageView imageView, BasicComponent entity) {
        for (Resource resource: myGame.resources) {
            if (entity.getMyFilename().equals(resource.resourceID)) {
                InputStream newInputStream = this.getClass().getResourceAsStream(resource.src);
                Image newImage = new Image(newInputStream);
                imageView.setImage(newImage);
            }
        }
    }

    private void initDataTrackers() {
        myXPosTracker = new NumericalDataTracker<>("X Position");
        myYPosTracker = new NumericalDataTracker<>("Y Position");
        myTimeTracker = new NumericalDataTracker<>("Time");
        myYVelocity = new NumericalDataTracker<>("Y Velocity");
        myLivesTracker = new NumericalDataTracker<>("Lives");
        myScoreTracker = new NumericalDataTracker<>("Score");
    }

    private void updateDataTrackers() {
        initComponents();
        storeData();
    }

    private void initComponents() {
        userEngineInstance = myLevelController.getUserEngineInstance();
        basicComponent = userEngineInstance.getComponent(BasicComponent.class);
        motionComponent = userEngineInstance.getComponent(MotionComponent.class);
        livesComponent = userEngineInstance.getComponent(LivesComponent.class);
        scoreComponent = userEngineInstance.getComponent(ScoreComponent.class);
    }

    private void storeData() {
        myTimeTracker.storeData(myCount * 1.0);
        myXPosTracker.storeData(basicComponent.getX());
        myYPosTracker.storeData(basicComponent.getY());
        myYVelocity.storeData(motionComponent.getYVelocity());
        myLivesTracker.storeData(livesComponent.getLives());
        myScoreTracker.storeData(scoreComponent.getScore());
    }

    public void updateLives(int lives) {
        livesComponent.setLives(lives);
    }

    public void restartGame() {
        myGameStage.close();
        myGameCenterController.launchPlayer();

    }

    private void setGamePaused() {
        gamePaused = myPlayerButtons.getGamePaused();
    }

    public int getGamePaused() {
        return gamePaused;
    }

    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        File potentialFile = fileChooser.showSaveDialog(myGameStage);
        save(potentialFile);
    }

    private void save(File file) {
        String contents = new Gson().toJson(myGame, new TypeToken<Game>(){}.getType());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(contents);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
    }

    public void storeScore() {
        String myFinalScore = (String) myScoreTracker.getLatestValue();
        myGameCenterController.setHighScore(myGameID, myFinalScore);
    }

    private void checkLevelOver() {

    }

    private void checkGameOver() {

    }

    private void endGame() {

    }

}
