package Engine.src.Controller;

import Engine.src.ECS.CollisionDetector;
import Engine.src.Manager.Manager;
import gamedata.Game;
import gamedata.GameObjects.Components.*;
import Engine.src.ECS.Pair;
import Engine.src.ECS.CollisionHandler;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.GameObjects.Instance;
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

    private Map<String, String> myHotKeys;
    private List<TimerSequence> myTimerSequences;
    private Map<Integer, Timer> myTimers;
    private Map<Pair<String>, Pair<String>> myCollisionResponses;
    private Set<Instance> myInstances;
    private String mySceneLogic;

    private Instance myUserInstance;
    private double myStepTime;
    private double myIterationCounter;
    private double[] myOffset;

    private CollisionHandler myCollisionHandler;
    private Manager myManager;
    private Game myGame;
    private DebugLog myDebugLog;
    private Sounds mySounds;

    private Binding myBinding;
    private GroovyShell myShell;

    public LevelController(double stepTime, double screenWidth, double screenHeight, double levelWidth, double levelHeight,
                           Game game, String sceneScript, int levelNumber) {

        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        myLevelWidth = levelWidth;
        myLevelHeight = levelHeight;

        myInstances = new HashSet<>();
        mySceneLogic = "";
        myCollisionResponses = new HashMap<>();
        myHotKeys = new HashMap<>();
        myTimerSequences = new ArrayList<>();
        myTimers = new HashMap<>();

        var parser = new EngineParser(myInstances, mySceneLogic, myCollisionResponses, myHotKeys, myTimerSequences, myTimers);
        parser.initializeDataTypes(sceneScript);

        myGame = game;
        Scene scene = new Scene(myInstances, mySceneLogic, Integer.toString(levelNumber));
        myGame.addScene(scene);
        myGame.setCurrentScene(scene);

        myIterationCounter = 0;
        myDebugLog = new DebugLog();
        myOffset = updateOffset();

        initializeGroovyShell();
        myManager = new Manager(myGame, myStepTime, myBinding);
        myCollisionHandler = new CollisionHandler(myManager);

        setUser();
    }

    private void initializeGroovyShell() {
        myBinding = new Binding();
        myBinding.setProperty("manager", myManager);
        myBinding.setProperty("collisionHandler", myCollisionHandler);
        myBinding.setProperty("collisionDetector", new CollisionDetector());
        myBinding.setProperty("debugLogger", myDebugLog);
        myShell = new GroovyShell(myBinding);
    }

    private void setUser(){
        for (Instance instance : myInstances) {
            String type = instance.getType();
            if (type.equals("USER")) {
                myUserInstance = instance;
                break;
            }
        }
    }

    public void processKey(String key) {
        if (myHotKeys.containsKey(key)) {
            String event = myHotKeys.get(key);
            GroovyShell shell = new GroovyShell(myBinding);
            myBinding.setProperty("instance", myUserInstance);
            Script script = shell.parse(event);
            script.run();
        } else ; //TODO:error
    }

    public void updateScene() {
        Script script = myShell.parse(mySceneLogic);
        script.run();
        myManager.executeEntityLogic();
        myManager.updateTimers();
        myManager.updateSequences();
        myCollisionHandler.handleCollisions(myInstances, myCollisionResponses);
        myOffset = updateOffset();
    }

    private double[] updateOffset() {
        BasicComponent basic = myUserInstance.getComponent(BasicComponent.class);
        double userX = basic.getX();
        double userY = basic.getY();
        double userWidth = basic.getWidth();
        double userHeight = basic.getHeight();
        return determineOffset(userX, userY, userWidth, userHeight, myScreenWidth, myScreenHeight);
    }


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

    public Set<Instance> getEntities() {
        return myInstances;
    }

    public double getScore(Instance instance) {
        ScoreComponent score = instance.getComponent(ScoreComponent.class);
        return score.getScore();
    }

    public Instance getUserInstance() {
        return myUserInstance;
    }

    public boolean levelPassed() {
        return myManager.levelPassed();
    }

//    public List<String> debugLog() {
//        return myDebugLog.getLog();
//    }

//    public Map<String, Boolean> playSound(String audioFilename) {
//        Map<String, Boolean> temp = mySounds.getSounds();
//        mySounds.clearSounds();
//        return temp;
//    }

}
