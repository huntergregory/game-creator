package Player.PlayerMain;

import Engine.src.Controller.GameController;
import Player.Features.PlayerButtons;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import GameCenter.main.GameCenterController;
import Engine.src.EngineData.EngineInstance;
import Player.Features.DebugConsole;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.HealthComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.Controller.LevelController;
import com.google.gson.stream.JsonReader;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Resource;
import gamedata.serialization.Serializer;
import hud.DataTracker;
import hud.HUDView;
import hud.NumericalDataTracker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

import static javafx.application.Application.launch;


//public class PlayerStage {
//    private final String STYLESHEET = "style.css";
//    private final double HUD_WIDTH = 300;
//
//    public final String ST_TITLE = "Cracking Open a Scrolled One with the Boys";
//    public final double ST_WIDTH = 800;
//    public final double ST_HEIGHT = 600;
//    public final Paint ST_COLOR = Color.web("284376");
//
//    public final double STEP_TIME = 5;
//    public final double GAME_WIDTH = 1400;
//    public final double GAME_HEIGHT = 800;
//    public final Paint GAME_BG = Color.BLACK;
//
//    public static final int FRAMES_PER_SECOND = 15;
//    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
//    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
//    private static final int HUD_UPDATE_DELAY = 10;
//    private static final boolean HUD_INCLUDES_PLOTTER = true;
//
//
//    private VBox myVBox;
//    private Game myGame;
//    private Stage myGameStage;
//    private Scene myScene;
//    private GridPane myVisualRoot;
//    private BorderPane myBorderPane;
//    private HUDView myHud;
//    private PlayerButtons myPlayerButtons;
//    private DebugConsole myDebugConsole;
//
//    private GameCenterController myGameCenterController;
//    private LevelController myLevelController;
//    private GameController myGameController;
//    private Pane myGameRoot;
//    private Map<String, EngineInstance> myEngineInstances;
//    private Map<EngineInstance, ImageView> myImageViewMap;
//    private List<MediaPlayer> mySounds;
//    private int myLevelNumber;
//
//    private NumericalDataTracker<Double> myXPosTracker;
//    private NumericalDataTracker<Double> myYPosTracker;
//    private NumericalDataTracker<Double> myYVelocity;
//    private NumericalDataTracker<Double> myTimeTracker;
//    private NumericalDataTracker<Integer> myLivesTracker;
//    private NumericalDataTracker<Integer> myScoreTracker;
//    private DataTracker<String> myPowerupTracker;
//
//    private final String FILE_NOT_FOUND = "File not found";
//    private int myCount;
//    private int gamePaused;
//    private Boolean debugMode = false;
//
//    //public PlayerStage(GameCenterController gameCenterController) {
////        myGameCenterController = myGameCenterController;
////        myVisualRoot = new GridPane();
////        //mySidePanelWidth = ST_WIDTH / 3.0;
////        //myLeftPanel = new SidePanel(mySidePanelWidth);
////        //myBorderPane = new BorderPane();
////        //myBorderPane.setLeft(myLeftPanel.getPane());
////        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
////        //myScene = new Scene(myBorderPane, ST_WIDTH, SCREEN_HEIGHT, ST_COLOR);
////        myScene.getStylesheets().add(STYLESHEET);
////        try {
////            new Serializer().serialize(List.of("parser.printHere(); parser.addCollision('Mario', 'Block', 'script');", "parser.addCollision('Mario', 'Turtle');"));
////        }
////        catch (IOException e) {
////            System.out.println("Couldn't write to file.");
////        }
//    //}
//
//    public static void main(String[] args) {
//        launch(args);
//
//    }
//
//    public void start(Stage stage) {
//        Resource userResource = new Resource();
//        userResource.resourceType = Resource.ResourceType.IMAGE_RESOURCE;
//        userResource.resourceID = "Mario Picture";
//        userResource.src = "/img/mario.jpg";
//        Resource blockResource = new Resource();
//        blockResource.resourceType = Resource.ResourceType.IMAGE_RESOURCE;
//        blockResource.resourceID = "Block Picture";
//        blockResource.src = "/img/block.jpg";
//        GameObject user = new GameObject();
//        user.objectID = "user";
//        user.objectLogic = "object.addComponent(" +
//                "new MotionComponent('0', '0', '10', '10', '0', '0.01'), new HealthComponent('100', '100'), new JumpComponent('5'), " +
//                "new LivesComponent('3', ' '), new ScoreComponent('0'))";
//        //new BasicComponent('/img/mario.jpg', '50.0', '100.0', '50.0', '50.0', '1'),
//        user.bgColor = "FFFFFF";
//        user.bgImage = "Mario Picture";
//        GameObject block = new GameObject();
//        block.objectID = "Block";
//        block.objectLogic = "object.addComponent(" +
//                "new MotionComponent('0', '0', '10', '10', '0', '0'), new HealthComponent('100', '100'), new JumpComponent('5'), " +
//                "new LivesComponent('3', ' '), new ScoreComponent('0'))";
//        //new BasicComponent('/img/block.jpg', '50.0', '50.0', '50.0', '50.0', '1'),
//        block.bgColor = "FFFFFF";
//        block.bgImage = "Block Picture";
//        Instance user1 = new Instance();
//        user1.instanceOf = "user";
//        user1.instanceID = "Mario";
//        user1.instanceLogic = "";
//        //instance.getComponent(BasicComponent.class).setX( (Double) 50.0)
//        user1.bgColor = "FFFFFF";
//        user1.bgImage = "Mario Picture";
//        user1.height = 50;
//        user1.width = 50;
//        user1.x = 150;
//        user1.y = 50;
//        user1.zIndex = 1;
//        Instance block1 = new Instance();
//        block1.instanceOf = "Block";
//        block1.instanceID = "Block1";
//        block1.instanceLogic = "";
//        block1.bgColor = "#FFFFFF";
//        block1.bgImage = "Block Picture";
//        block1.height = 50;
//        block1.width = 500;
//        block1.x = 0;
//        block1.y = 200;
//        block1.zIndex = 2;
//        gamedata.Scene scene1 = new gamedata.Scene();
//        scene1.instances.add(user1);
//        scene1.instances.add(block1);
//        //scene1.sceneLogic = "";
//        scene1.sceneID = "Level1";
//        scene1.bgColor = "";
//        scene1.bgImage = "";
//        Game game = new Game();
//        game.scenes.add(scene1);
//        game.gameObjects.add(user);
//        game.gameObjects.add(block);
//        game.resources.add(userResource);
//        game.resources.add(blockResource);
//        myGameStage = stage;
//    }
//
//    public PlayerStage(GameCenterController gameCenterController) {
//        myGameCenterController = myGameCenterController;
//        myVisualRoot = new GridPane();
//        //mySidePanelWidth = ST_WIDTH / 3.0;
//        //myLeftPanel = new SidePanel(mySidePanelWidth);
//        //myBorderPane = new BorderPane();
//        //myBorderPane.setLeft(myLeftPanel.getPane());
//        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
//        //myScene = new Scene(myBorderPane, ST_WIDTH, SCREEN_HEIGHT, ST_COLOR);
//        myScene.getStylesheets().add(STYLESHEET);
//        try {
//            new Serializer().serialize(List.of("parser.printHere(); parser.addCollision('Mario', 'Block', 'script');", "parser.addCollision('Mario', 'Turtle');"));
//        }
//        catch (IOException e) {
//            System.out.println("Couldn't write to file.");
//        }
//    }
//
//    public void run(Game game, Boolean debug) {
//        debugMode = debug;
//        startNewLevel();
//    }
//
//    public void save(File file){
//        String contents = new Gson().toJson(myGame, new TypeToken<Game>(){}.getType());
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
//            writer.write(contents);
//            writer.close();
//        }
//        catch (IOException e){
//            System.out.println(FILE_NOT_FOUND);
//        }
//    }
//
//    public void load(String fileName) {
//            String contents = new Scanner(fileName).useDelimiter("\\Z").next();
//            myGame = new Gson().fromJson(contents, new TypeToken<Game>() {}.getType());
//            myGameController = new GameController(MILLISECOND_DELAY, ST_WIDTH, ST_HEIGHT, GAME_WIDTH, GAME_HEIGHT, myGame);
//            myLevelNumber = myGame.currentLevel;
//            startNewLevel();
//    }
//
//    private void startNewLevel() {
//        myLevelController = myGameController.getLevelController();
//        myEngineInstances = myLevelController.getEngineInstances();
//        //myGameStage = new Stage(); //FIXME ExceptionInInitializerError
//        myImageViewMap = new HashMap<>();
//        initAndRemoveSounds();
//        initDataTrackers();
//        initBorderPane();
//        addNewImageViews();
//        Scene gameScene = new Scene(myBorderPane, GAME_BG);
//        gameScene.getStylesheets().add("hud.css");
//        myGameStage.setScene(gameScene);
//        myGameStage.show();
//        gameScene.setOnKeyPressed(e -> myLevelController.processKey(e.getCode().toString()));
//        animate();
//    }
//
//    private void setHud() {
//        myHud = new HUDView(HUD_WIDTH, ST_HEIGHT, "GameLoader 1", HUD_INCLUDES_PLOTTER, myXPosTracker,
//                myYPosTracker,
//                myYVelocity,
//                myTimeTracker,
//                myLivesTracker,
//                myPowerupTracker);
//    }
//
//    private void setPlayerButtons() {
//        myPlayerButtons = new PlayerButtons(this);
//    }
//
//    private void setDebugConsole() {
//        myDebugConsole = new DebugConsole(HUD_WIDTH, ST_HEIGHT);
//    }
//
//    private void initBorderPane() {
//        myBorderPane = new BorderPane();
//        myGameRoot = new Pane();
//        myBorderPane.setCenter(myGameRoot);
//        setHud();
//        setPlayerButtons();
//        myVBox = new VBox();
//        myVBox.getChildren().add(myPlayerButtons.getNode());
//        if (debugMode) {
//            setDebugConsole();
//            myDebugConsole.addText("");
//            myVBox.getChildren().add(myDebugConsole.getMainComponent());
//        }
//        myBorderPane.setLeft(myHud.getNode());
//        myBorderPane.setRight(myVBox);
//    }
//
//    private void animate() {
//        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
//        var animation = new Timeline();
//        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.getKeyFrames().add(frame);
//        animation.play();
//    }
//
//    private void step() {
//        setGamePaused();
//        if (gamePaused == 0) {
//            if (myLevelController.levelPassed()) {
//                myGame.currentLevel = myGame.currentLevel + 1;
//                startNewLevel();
//            }
//            myLevelController.updateScene();
//            addNewImageViews();
//            updateOrRemoveImageViews();
//            updateDebugLog();
//            updateSounds();
//            if (myCount % HUD_UPDATE_DELAY == 0) {
//                updateDataTrackers();
//                myHud.update();
//            }
//            myCount++;
//        }
//    }
//
//    private void updateDebugLog() {
//        if (debugMode) {
//            List<String> debugLog = myLevelController.debugLog();
//            myDebugConsole.update(debugLog);
//        }
//    }
//
//    private void updateSounds() {
////        Media sound = new Media(new File(sound.getKey()).toURI().toString());
////        MediaPlayer mediaPlayer = new MediaPlayer(sound);
////        mediaPlayer.play();
//        Map<String, Boolean> sounds = myLevelController.playSound();
//        for(Map.Entry<String, Boolean> sound : sounds.entrySet()) {
//            MediaPlayer a = new MediaPlayer(new Media(getClass().getResource(sound.getKey()).toString()));
//            if(sound.getValue()) {
//                a.setOnEndOfMedia(() -> a.seek(Duration.ZERO));
//            }
//            else {
//                a.setOnEndOfMedia(() -> mySounds.remove(a));
//            }
//            mySounds.add(a);
//            a.play();
//        }
//    }
//
//    private void initAndRemoveSounds() {
//        if (mySounds == null) {
//            mySounds = new ArrayList<>();
//            return;
//        }
//        for(MediaPlayer m: mySounds) {
//            m.setMute(true);
//        }
//        mySounds.clear();
//    }
//
//    private void updateOrRemoveImageViews() {
//        for (EngineInstance engineInstance : myImageViewMap.keySet()) {
//            //FIXME removes imageview from game root without the !
//            if (!myEngineInstances.containsKey(engineInstance.getID()))
//                myGameRoot.getChildren().remove(myImageViewMap.get(engineInstance));
//            updateImageView(engineInstance);
//        }
//    }
//
//    private void addNewImageViews() {
//        for (String ID : myEngineInstances.keySet()) {
//            EngineInstance engineInstance = myEngineInstances.get(ID);
//            if (myImageViewMap.containsKey(engineInstance))
//                continue;
//            var newImageView = new ImageView();
//            myImageViewMap.put(engineInstance, newImageView);
//            myGameRoot.getChildren().add(newImageView);
//            updateImageView(engineInstance);
//        }
//    }
//
//    private void updateImageView(EngineInstance engineInstance) {
//        BasicComponent basicComponent = engineInstance.getComponent(BasicComponent.class);
//        //MotionComponent motionComponent = engineInstance.getComponent(MotionComponent.class);
//        //HealthComponent healthComponent = engineInstance.getComponent(HealthComponent.class);
//        if (basicComponent == null)
//            return;
//        //FIXME is it instance.getID or is it instance
//        ImageView imageView = myImageViewMap.get(engineInstance);
//        setImageIfNecessary(imageView, basicComponent);
//        moveAndResize(imageView, basicComponent);
//    }
//
//    private void moveAndResize(ImageView imageView, BasicComponent basicComponent) {
//        imageView.setX(basicComponent.getX() - myLevelController.getOffset()[0]);
//        imageView.setY(basicComponent.getY() - myLevelController.getOffset()[1]);
//        imageView.setFitWidth(basicComponent.getWidth());
//        imageView.setFitHeight(basicComponent.getHeight());
//    }
//
//    private void setImageIfNecessary(ImageView imageView, BasicComponent entity) {
//        //Set filename to one based on resource
//        InputStream newInputStream = null;
//        for(Resource resource: myGame.resources) {
//            if(entity.getMyFilename().equals(resource.resourceID)) {
//                newInputStream = this.getClass().getResourceAsStream(resource.src);
//            }
//        }
//        if (newInputStream == null) {
//            return;
//        }
//
//        Image newImage = new Image(newInputStream);
//        if (!newImage.equals(imageView.getImage()))
//            imageView.setImage(newImage);
//    }
//
//    private void initDataTrackers() {
//        myXPosTracker = new NumericalDataTracker<>("X Position");
//        myYPosTracker = new NumericalDataTracker<>("Y Position");
//        myTimeTracker = new NumericalDataTracker<>("Time");
//        myYVelocity = new NumericalDataTracker<>("Y Velocity");
//        myLivesTracker = new NumericalDataTracker<>("Lives");
//        myScoreTracker = new NumericalDataTracker<>("Score");
//        myPowerupTracker = new DataTracker<>("Powerup");
//    }
//
//    private void updateDataTrackers() {
//        EngineInstance userEngineInstance = myLevelController.getUserEngineInstance();
//        BasicComponent basicComponent = userEngineInstance.getComponent(BasicComponent.class);
//        MotionComponent motionComponent = userEngineInstance.getComponent(MotionComponent.class);
//        myTimeTracker.storeData(myCount * 1.0); //TODO get actual time
//        myXPosTracker.storeData(basicComponent.getX());
//        myYPosTracker.storeData(basicComponent.getY());
//        myYVelocity.storeData(motionComponent.getYVelocity());
//        myLivesTracker.storeData(2); //FIXME
//        myScoreTracker.storeData(0); //FIXME
//        myPowerupTracker.storeData("Flower"); //FIXME
//    }
//
//    /**
//     * edit(), rate() currently placeholder. Update these methods.
//     */
//    public void edit(String gameName) {
////        System.out.println(gameName + " is being edited!");
//    }
//
//    public void rate(String gameName) {
////        System.out.println("Rating for " + gameName + " is being changed!");
//    }
//
//    public Stage makeStage() {
//        Stage ret = new Stage();
//        ret.setTitle(ST_TITLE);
//        ret.setScene(myScene);
//        return ret;
//    }
//
//    public void updateLives(int lives) {
//        System.out.println(lives);
//    }
//
//    public void updateTime(int time) {
//        System.out.println(time);
//    }
//
//    public void restartGame() {
//        myGameStage.close();
//
//    }
//
//    private void setGamePaused() {
//        gamePaused = myPlayerButtons.getGamePaused();
//    }
//
//    public int getGamePaused() {
//        return gamePaused;
//    }
//
//    public void saveGame() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
//        File potentialFile = fileChooser.showSaveDialog(myGameStage);
//        save(potentialFile);
//    }
//
//    public void storeScore() {
//        int myFinalScore = (int) myScoreTracker.getLatestValue();
//    }
//}

//package Player.PlayerMain;

        import Engine.src.Controller.GameController;
        import Player.Features.PlayerButtons;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;
        import GameCenter.main.GameCenterController;
        import Engine.src.EngineData.EngineInstance;
        import Player.Features.DebugConsole;
        import Engine.src.EngineData.Components.BasicComponent;
        import Engine.src.EngineData.Components.HealthComponent;
        import Engine.src.EngineData.Components.MotionComponent;
        import Engine.src.Controller.LevelController;
        import gamedata.Game;
        import gamedata.GameObject;
        import gamedata.Instance;
        import gamedata.Resource;
        import gamedata.serialization.Serializer;
        import hud.DataTracker;
        import hud.HUDView;
        import hud.NumericalDataTracker;
        import javafx.animation.KeyFrame;
        import javafx.animation.Timeline;
        import javafx.application.Application;
        import javafx.collections.ObservableList;
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

        import static javafx.application.Application.launch;


public class PlayerStage extends Application {
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

    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
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
    private int myLevelNumber;

    private NumericalDataTracker<Double> myXPosTracker;
    private NumericalDataTracker<Double> myYPosTracker;
    private NumericalDataTracker<Double> myYVelocity;
    private NumericalDataTracker<Double> myTimeTracker;
    private NumericalDataTracker<Integer> myLivesTracker;
    private NumericalDataTracker<Integer> myScoreTracker;
    private DataTracker<String> myPowerupTracker;

    private final String FILE_NOT_FOUND = "File not found";
    private int myCount;
    private int gamePaused;
    private Boolean debugMode = false;

    //public PlayerStage(GameCenterController gameCenterController) {
//        myGameCenterController = myGameCenterController;
//        myVisualRoot = new GridPane();
//        //mySidePanelWidth = ST_WIDTH / 3.0;
//        //myLeftPanel = new SidePanel(mySidePanelWidth);
//        //myBorderPane = new BorderPane();
//        //myBorderPane.setLeft(myLeftPanel.getPane());
//        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
//        //myScene = new Scene(myBorderPane, ST_WIDTH, SCREEN_HEIGHT, ST_COLOR);
//        myScene.getStylesheets().add(STYLESHEET);
//        try {
//            new Serializer().serialize(List.of("parser.printHere(); parser.addCollision('Mario', 'Block', 'script');", "parser.addCollision('Mario', 'Turtle');"));
//        }
//        catch (IOException e) {
//            System.out.println("Couldn't write to file.");
//        }
    //}

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        Resource userResource = new Resource();
        userResource.resourceType = Resource.ResourceType.IMAGE_RESOURCE;
        userResource.resourceID = "Mario Picture";
        userResource.src = "/img/mario.jpg";
        Resource blockResource = new Resource();
        blockResource.resourceType = Resource.ResourceType.IMAGE_RESOURCE;
        blockResource.resourceID = "Block Picture";
        blockResource.src = "/img/block.jpg";
        GameObject user = new GameObject();
        user.objectID = "user";
        user.objectLogic = "object.addComponent(new MotionComponent('0', '0', '10', '10', '0', '1'), new HealthComponent('100', '100'), new JumpComponent('5'), new LivesComponent('3', 'instance.getComponent(BasicComponent).setX((Double) 800)'), new ScoreComponent('0'), new LogicComponent('if(instance.getComponent(HealthComponent).getHealth() < 0) { manager.call(\"Die\", instance); manager.call(\"AddToHealth\", instance, 100) }'));";
        //new BasicComponent('/img/mario.jpg', '50.0', '100.0', '50.0', '50.0', '1'),
        user.bgColor = "FFFFFF";
        user.bgImage = "Mario Picture";
        GameObject block = new GameObject();
        block.objectID = "Block";
        block.objectLogic = "object.addComponent(new MotionComponent('0', '0', '10', '10', '0', '0'), new HealthComponent('100', '100'), new ImpassableComponent('true'))";
        //new BasicComponent('/img/block.jpg', '50.0', '50.0', '50.0', '50.0', '1'),
        block.bgColor = "FFFFFF";
        block.bgImage = "Block Picture";
        Instance user1 = new Instance();
        user1.instanceOf = "user";
        user1.instanceID = "Mario";
        user1.instanceLogic = "";
        //instance.getComponent(BasicComponent.class).setX( (Double) 50.0)
        user1.bgColor = "FFFFFF";
        user1.bgImage = "Mario Picture";
        user1.height = 50;
        user1.width = 50;
        user1.x = 150;
        user1.y = 50;
        user1.zIndex = 1;
        Instance block1 = new Instance();
        block1.instanceOf = "Block";
        block1.instanceID = "Block1";
        block1.instanceLogic = "";
        block1.bgColor = "#FFFFFF";
        block1.bgImage = "Block Picture";
        block1.height = 50;
        block1.width = 500;
        block1.x = 0;
        block1.y = 200;
        block1.zIndex = 2;
        gamedata.Scene scene1 = new gamedata.Scene();
        scene1.instances.add(user1);
        scene1.instances.add(block1);
        scene1.sceneLogic = "parser.addKey('D', 'manager.call(\"KeyMoveRight\", instance)');" +
                "parser.addKey('A', 'manager.call(\"KeyMoveLeft\", instance)');" +
                "parser.addKey('W', 'manager.call(\"KeyMoveUp\", instance)');" +
                "parser.addKey('S', 'manager.call(\"KeyMoveDown\", instance)');" +
                "parser.addKey('M', 'manager.call(\"Jump\", instance)');" +
                "parser.addCollision('user', 'Block', 'manager.call(\"IncreaseScore\", object1, (Double) 50)');";
        scene1.sceneID = "Level1";
        scene1.bgColor = "";
        scene1.bgImage = "";
        gamedata.Scene scene2 = new gamedata.Scene();
        scene2.instances.add(user1);
        scene2.instances.add(block1);
        Game game = new Game();
        game.scenes.add(scene1);
        game.gameObjects.add(user);
        game.gameObjects.add(block);
        game.resources.add(userResource);
        game.resources.add(blockResource);
        myGameStage = stage;
        myGameCenterController = myGameCenterController;
        myVisualRoot = new GridPane();
        //mySidePanelWidth = ST_WIDTH / 3.0;
        //myLeftPanel = new SidePanel(mySidePanelWidth);
        //myBorderPane = new BorderPane();
        //myBorderPane.setLeft(myLeftPanel.getPane());
        myScene = new Scene(myVisualRoot, ST_WIDTH, ST_HEIGHT, ST_COLOR);
        //myScene = new Scene(myBorderPane, ST_WIDTH, SCREEN_HEIGHT, ST_COLOR);
        myScene.getStylesheets().add(STYLESHEET);
        try {
            new Serializer().serialize(List.of("parser.printHere(); parser.addCollision('Mario', 'Block', 'script');", "parser.addCollision('Mario', 'Turtle');"));
        }
        catch (IOException e) {
            System.out.println("Couldn't write to file.");
        }
        myGameStage.setScene(myScene);
        load("/Users/dliu18/Duke/Classes/CS308/voogasalad_crackingopen/data/DLiu.game");
        //load(game);
    }

    public void run(Game game, Boolean debug) {
        debugMode = debug;
        startNewLevel();
    }

    public void save(File file){
        String contents = new Gson().toJson(myGame, new TypeToken<Game>(){}.getType());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(contents);
            writer.close();
        }
        catch (IOException e){
            System.out.println(FILE_NOT_FOUND);
        }
    }

    public void load(String filename) {
//        Gson gson = new Gson();
//        JsonReader reader = new JsonReader(new StringReader(filename));
//        reader.setLenient(true);
//        Game g = gson.fromJson(reader, Game.class);
        String contents = null;
        try {
            contents = new Scanner(new File(filename)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
        myGame = new Gson().fromJson(contents, new TypeToken<Game>() {}.getType());
        myGameController = new GameController(STEP_TIME, ST_WIDTH, ST_HEIGHT, myGame);
        myLevelNumber = myGame.currentLevel;
        startNewLevel();
    }

    public void load(Game game) {
        //String contents = new Scanner(fileName).useDelimiter("\\Z").next();
        //myGame = new Gson().fromJson(contents, new TypeToken<Game>() {}.getType());
//            myGame = new Game();
//            myGame.scenes = new ArrayList<>();
//            gamedata.Scene scene = new gamedata.Scene();
//            scene.instances = new HashSet<>();
//            scene.instances.add(new Instance());
//            myGame.scenes.add(scene);
        myGame = game;
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
        addNewImageViews();
        Scene gameScene = new Scene(myBorderPane, GAME_BG);
        gameScene.getStylesheets().add("hud.css");
        myGameStage.setScene(gameScene);
        myGameStage.show();
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

    private void updateDebugLog() {
        if (debugMode) {
            List<String> debugLog = myLevelController.debugLog();
            myDebugConsole.update(debugLog);
        }
    }

    private void updateSounds() {
//        Media sound = new Media(new File(sound.getKey()).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
        Map<String, Boolean> sounds = myLevelController.playSound();
        for(Map.Entry<String, Boolean> sound : sounds.entrySet()) {
            MediaPlayer a = new MediaPlayer(new Media(getClass().getResource(sound.getKey()).toString()));
            if(sound.getValue()) {
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
        for(MediaPlayer m: mySounds) {
            m.setMute(true);
        }
        mySounds.clear();
    }

    private void updateOrRemoveImageViews() {
        for (EngineInstance engineInstance : myImageViewMap.keySet()) {
            //FIXME removes imageview from game root without the !
            if (!myEngineInstances.containsKey(engineInstance.getID()))
                myGameRoot.getChildren().remove(myImageViewMap.get(engineInstance));
            updateImageView(engineInstance);
        }
    }

    private void addNewImageViews() {
        for (String ID : myEngineInstances.keySet()) {
            EngineInstance engineInstance = myEngineInstances.get(ID);
            if (myImageViewMap.containsKey(engineInstance))
                continue;
            var newImageView = new ImageView();
            myImageViewMap.put(engineInstance, newImageView);
            myGameRoot.getChildren().add(newImageView);
            updateImageView(engineInstance);
        }
    }

    private void updateImageView(EngineInstance engineInstance) {
        BasicComponent basicComponent = engineInstance.getComponent(BasicComponent.class);
        //MotionComponent motionComponent = engineInstance.getComponent(MotionComponent.class);
        //HealthComponent healthComponent = engineInstance.getComponent(HealthComponent.class);
        if (basicComponent == null)
            return;
        //FIXME is it instance.getID or is it instance
        ImageView imageView = myImageViewMap.get(engineInstance);
        setImageIfNecessary(imageView, basicComponent);
        moveAndResize(imageView, basicComponent);
    }

    private void moveAndResize(ImageView imageView, BasicComponent basicComponent) {
        imageView.setX(basicComponent.getX() - myLevelController.getOffset()[0]);
        imageView.setY(basicComponent.getY() - myLevelController.getOffset()[1]);
        imageView.setFitWidth(basicComponent.getWidth());
        imageView.setFitHeight(basicComponent.getHeight());
    }

    private void setImageIfNecessary(ImageView imageView, BasicComponent entity) {
        //Set filename to one based on resource
        InputStream newInputStream = null;
        for(Resource resource: myGame.resources) {
            if(entity.getMyFilename().equals(resource.resourceID)) {
                newInputStream = this.getClass().getResourceAsStream(resource.src);
            }
        }
        if (newInputStream == null) {
            return;
        }

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
        EngineInstance userEngineInstance = myLevelController.getUserEngineInstance();
        BasicComponent basicComponent = userEngineInstance.getComponent(BasicComponent.class);
        MotionComponent motionComponent = userEngineInstance.getComponent(MotionComponent.class);
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

    public void updateLives(int lives) {
        System.out.println(lives);
    }

    public void updateTime(int time) {
        System.out.println(time);
    }

    public void restartGame() {
        myGameStage.close();

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

    public void storeScore() {
        int myFinalScore = (int) myScoreTracker.getLatestValue();
    }

}
