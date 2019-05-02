package Engine.src.Controller;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.LogicComponent;
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

    private final double myScreenWidth;
    private final double myScreenHeight;
    private double[] myOffset;
    private boolean scrollsHorizontally;
    private boolean scrollsVertically;

    private double myStepTime;
    private double myIterationCounter;

    private TimerController myTimerController;
    private EngineParser myParser;
    private CollisionHandler myCollisionHandler;
    private Manager myManager;
    private Game myGame;
    private DebugLog myDebugLog;
    private Sounds mySounds;

    private Binding myBinding;
    private GroovyShell myShell;

    public LevelController(double stepTime, double screenWidth, double screenHeight, Game game) {

        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;

        myGame = game;

        myParser = new EngineParser(myGame);

        scrollsHorizontally = myParser.getHorizScrolling();
        scrollsVertically = myParser.getVertScrolling();

        myIterationCounter = 0;
        myDebugLog = new DebugLog();
        myOffset = updateOffset();
        initializeGroovyShell();
        myTimerController = new TimerController(myShell);
        myManager = new Manager(myParser.getEngineInstances(), myTimerController, myStepTime);
        myCollisionHandler = new CollisionHandler(myManager);
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
        executeEntityLogic();
        myTimerController.update();
        myCollisionHandler.handleCollisions(myParser.getEngineInstances(), myParser.getCollisions());
        myOffset = updateOffset();
    }

    private void executeEntityLogic() {
        for (String ID : myParser.getEngineInstances().keySet()) {
            EngineInstance engineInstance = myParser.getEngineInstances().get(ID);
            LogicComponent logicComponent = engineInstance.getComponent(LogicComponent.class);
            String logic = logicComponent.getLogic();
            myBinding.setProperty(engineInstance.getID(), engineInstance);
            Script script = myShell.parse(logic);
            script.run();
        }
    }

    private double[] updateOffset() {
        BasicComponent basic = myParser.getUserEngineInstance().getComponent(BasicComponent.class);
        double userX = basic.getX();
        double userY = basic.getY();
        double userWidth = basic.getWidth();
        double userHeight = basic.getHeight();
        return determineOffset(userX, userY, userWidth, userHeight, myScreenWidth, myScreenHeight);
    }


    // determine offset between actual position (within entire level) and display position (within screen), which places
    // the user always in the center if the game is supposed to scroll
    private double[] determineOffset(double userX, double userY, double userWidth, double userHeight, double screenWidth,
                                    double screenHeight) {
        double offsetX = 0;
        double offsetY = 0;

        if (scrollsHorizontally) {
            // if user is close to the left edge of the level, no scrolling to avoid displaying out of bounds area
            if (userX <= .5 * screenWidth - .5 * userWidth) {
                offsetX = 0;
            } else {
                offsetX = userX + .5 * userWidth - .5 * screenWidth;
            }
        }
        if (scrollsVertically) {
            if (userY <= .5 * screenHeight - .5 * userHeight) {
                offsetY = 0;
            } else {
                offsetY = userY + .5 * userHeight - .5 * screenHeight; // this puts the user 3/4 the way dow the screen
            }
        }

        return new double[]{offsetX, offsetY};
    }

    public double[] getOffset() {
        return myOffset;
    }

    public Map<String, EngineInstance> getEngineInstances() {
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

    public Map<String, Boolean> playSound() {
        Map<String, Boolean> temp = mySounds.getSounds();
        mySounds.clearSounds();
        return temp;
    }

}
