package Engine.src.Controller;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.ScoreComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Manager.DebugLog;
import Engine.src.Manager.Manager;
import Engine.src.Manager.Sounds;
import gamedata.Game;
import Engine.src.ECS.Pair;
import Engine.src.ECS.CollisionHandler;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Scene;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;

public class LevelController {
    //FIXME remove eventually
    private static final boolean SCROLLS_HORIZONTALLY = true;
    private static final boolean SCROLLS_VERTICALLY = false;
    private static final boolean IS_AUTO_SCROLLER = false;
    private static final double SCROLL_SPEED = 10;                        //if auto scroller
    private static final double CHARACTER_DISTANCE_FROM_SCROLL_WALL = 20; //if auto scroller
    private static final double START_X = 0;
    private static final double START_Y = 0;
    //FIXME remove eventually

    private final double myScreenWidth;
    private final double myScreenHeight;
    private double myLevelWidth;
    private double myLevelHeight;

    private double myStepTime;
    private double myIterationCounter;
    private double[] myOffset;

    private EngineParser myParser;
    private CollisionHandler myCollisionHandler;
    private Manager myManager;
    private Game myGame;
    private DebugLog myDebugLog;

    private Sounds mySounds;

    private Binding myBinding;
    private GroovyShell myShell;

    public LevelController(double stepTime, double screenWidth, double screenHeight, double levelWidth, double levelHeight,
                           Game game) {

        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        myLevelWidth = levelWidth;
        myLevelHeight = levelHeight;

        myGame = game;
        Integer levelIndex = game.currentLevel;
        Scene scene = myGame.scenes.get(levelIndex);
        Set<Instance> serializedInstances = scene.instances;
        List<GameObject> serializedObjects = game.gameObjects;
        String sceneLogic = scene.sceneLogic;

        myParser = new EngineParser();
        myParser.parse(sceneLogic, serializedObjects, serializedInstances);

        initializeDataTypes("parser.printMessage('Dang!')");

        for (Pair<String> objectPair : myParser.getCollisions().keySet()) {
            System.out.println(objectPair.getItem1() + " with " + objectPair.getItem2() + myParser.getCollisions().get(objectPair));
        }

        myIterationCounter = 0;
        myDebugLog = new DebugLog();
        myOffset = updateOffset();
        initializeGroovyShell();
        myManager = new Manager(myParser.getEngineInstances(), myStepTime, myBinding);
        myCollisionHandler = new CollisionHandler(myManager);
    }

    private void initializeDataTypes(String sceneLogic){
        System.out.println(sceneLogic);
        Binding binding = new Binding();
        binding.setProperty("parser", myParser);
        GroovyShell shell = new GroovyShell(binding);
        Script script = shell.parse(sceneLogic);
        script.run();
    }

    private void initializeGroovyShell() {
        myBinding = new Binding();
        myBinding.setProperty("manager", myManager);
        myBinding.setProperty("collisionHandler", myCollisionHandler);
        myBinding.setProperty("collisionDetector", new CollisionDetector());
        myBinding.setProperty("debugLogger", myDebugLog);
        myShell = new GroovyShell(myBinding);
    }

    public void processKey(String key) {
        if (myParser.getHotKeys().containsKey(key)) {
            String event = myParser.getHotKeys().get(key);
            GroovyShell shell = new GroovyShell(myBinding);
            myBinding.setProperty("instance", myParser.getUserEngineInstance());
            Script script = shell.parse(event);
            script.run();
        } else ; //TODO:error
    }

    public void updateScene() {
        Script script = myShell.parse(myParser.getLevelRules());
        script.run();
        myManager.executeEntityLogic();
        myManager.updateTimers();
        myManager.updateSequences();
        myManager.updateCount();
        myCollisionHandler.handleCollisions(myParser.getEngineInstances(), myParser.getCollisions());
        myOffset = updateOffset();
    }

    private double[] updateOffset() {
        BasicComponent basic = myParser.getUserEngineInstance().getComponent(BasicComponent.class);
        double userX = basic.getX();
        double userY = basic.getY();
        double userWidth = basic.getWidth();
        double userHeight = basic.getHeight();
        return determineOffset(userX, userY, userWidth, userHeight, myScreenWidth, myScreenHeight);
    }


    //FIXME doesn't modify y offset
    public double[] determineOffset(double userX, double userY, double userWidth, double userHeight, double screenWidth,
                                    double screenHeight) {
        double offsetX;
        double offsetY;

        if (userX <= .5 * screenWidth - .5 * userWidth) {
            offsetX = 0;
        }
        /*else if (myLevelWidth - userX <= .5 * screenWidth + .5 * userWidth) {
            offsetX = myLevelWidth - screenWidth;
        }*/ //FIXME restricting max scroll to very small even when level width is large...
        else {
            offsetX = userX + .5 * userWidth - .5 * screenWidth;
        }

        if (userY <= .5 * screenHeight - .5 * userHeight) {
            offsetY = 0;
        }
        else if (myLevelHeight - userY <= .5 * screenHeight + .5 * userHeight) {
            offsetY = myLevelHeight - screenHeight;
        }
        else {
            offsetY = userY + .5 * userHeight - .75 * screenHeight; // this puts the user 3/4 the way dow the screen
        }

        return new double[]{offsetX, 0}; //FIXME hardcoding 0 offset in y direction for demo
    }

    public double[] getOffset() {
        return myOffset;
    }

    public Set<EngineInstance> getEngineInstances() {
        return myParser.getEngineInstances();
    }

    public double getScore(EngineInstance engineInstance) {
        ScoreComponent score = engineInstance.getComponent(ScoreComponent.class);
        return score.getScore();
    }

    public EngineInstance getUserEngineInstance() {
        return myParser.getUserEngineInstance();
    }

    public boolean levelPassed() {
        return myManager.levelPassed();
    }

    public List<String> debugLog() {
        return myDebugLog.getLog();
    }

//    public Map<String, Boolean> playSound(String audioFilename) {
//        Map<String, Boolean> temp = mySounds.getSounds();
//        mySounds.clearSounds();
//        return temp;
//    }

}
