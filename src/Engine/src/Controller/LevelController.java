package Engine.src.Controller;

import Engine.src.ECS.CollisionDetector;
import Engine.src.EngineData.ComponentExceptions.NoComponentException;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.ScoreComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Controller.DebugLog;
import Engine.src.Controller.Sounds;
import gamedata.Game;
import Engine.src.ECS.CollisionHandler;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;

public class LevelController {
    private static final String LOGIC_COMPONENT_KEYWORD = "instance";

    private final double myScreenWidth;
    private final double myScreenHeight;
    private double myLevelWidth;
    private double myLevelHeight;

    private double myStepTime;
    private double myIterationCounter;
    private double[] myOffset;

    private TimerController myTimerController;
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

        myParser = new EngineParser(myGame);

        myIterationCounter = 0;
        myDebugLog = new DebugLog();
        mySounds = new Sounds();
        myOffset = updateOffset();
        myTimerController = new TimerController(myShell);
        myManager = new Manager(myParser, myTimerController, myStepTime);
        myCollisionHandler = new CollisionHandler(myManager);
        initializeGroovyShell();
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
            try {
                LogicComponent logicComponent = engineInstance.getComponent(LogicComponent.class);
                String logic = logicComponent.getLogic();
                myBinding.setProperty(LOGIC_COMPONENT_KEYWORD, engineInstance);
                Script script = myShell.parse(logic);
                script.run();
            }
            catch(NoComponentException e) {
                System.out.println("No Component");
            }
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


    //FIXME doesn't modify y offset
    private double[] determineOffset(double userX, double userY, double userWidth, double userHeight, double screenWidth,
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
