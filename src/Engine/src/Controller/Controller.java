package Engine.src.Controller;

import Engine.src.Components.*;
import Engine.src.ECS.AI;
import Engine.src.ECS.EntityManager;
import Engine.src.ECS.Pair;
import Engine.src.ECS.CollisionHandler;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
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

    private Map<String, String> myHotKeys;
    //private List<TimerSequence> myTimerSequences;
    //private List<Timer> myTimers;
    private Map<Pair<String>, Pair<String>> myCollisionResponses;
    private Map<Integer, Map<Class<? extends Component>, Component>> myActiveObjects;
    private String myTriggers;

    private int myUserID;
    private double myStepTime;
    private double myIterationCounter;
    private double[] myOffset;

    private CollisionHandler myCollisionHandler;
    private EntityManager myEntityManager;
    private LevelManager myLevelManager;
    private AI myAI;

    private Binding myBinding;

    public Controller(double stepTime, double screenWidth, double screenHeight, double levelWidth, double levelHeight) {
        myHotKeys = new HashMap<>();
        myTimers = new ArrayList<>();
        myCollisionResponses = new HashMap<>();
        myTriggers = "";
        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        //myDataManager = new DataManager();
        initializeDataVariables();
        myLevelManager = new LevelManager(myTimers, myTimerSequences, myEntityManager, myIterationCounter, levelWidth, levelHeight);
        myEntityManager = new EntityManager(myActiveObjects, myStepTime);
        myCollisionHandler = new CollisionHandler(myEntityManager, myLevelManager);
        myIterationCounter = 0;

        setDefaultKeys();
        setDefaultTriggers();
        myOffset = updateOffset();
    }

    //FIXME??
    public void initializeDataVariables() {
        myActiveObjects = new DefaultGame().getActiveObjects(); //FIXME remove for non default, hardcoded game
        //myCollisionResponses = new DefaultGame().getCollisionMap();
        //myActiveObjects = myDataManager.loadDefaultObjects();
        //myHotKeys = myDataManager.loadHotKeyMap();
        //myCollisionResponses = myDataManager.loadCollisionResponseMap();
        //myTimers = myDataManager.loadTimerMap();
        //myTriggers = myDataManager.loadTriggers();
        myAI = new AI(myEntityManager);
        for (int id : myActiveObjects.keySet()) {
            Component type = myActiveObjects.get(id).get(TagsComponent.class);
            if (((TagsComponent) type).contains("USER")) {
                myUserID = id;
                break;
            }
        }
        myBinding.setProperty("entityManager", myEntityManager);
    }

    private void setDefaultKeys() {
        //myHotKeys.put("A", new MoveLeft(myUserID));
        //myHotKeys.put("D", new MoveRight(myUserID));
        //myHotKeys.put("SPACE", new Jump(myUserID));
    }

    private void setDefaultTriggers() {
        /*
        for(Integer id : myActiveObjects.keySet()) {
            Component health = myEntityManager.getComponent(id, HealthComponent.class);
            if (health != null) {
                List<Conditional> conditionals = new ArrayList<>();
                conditionals.add(new HealthComparison(true, id, "<=", new HealthComponent(0, 0)));
                myTriggers.add(new Die(conditionals, id));
            }
        }
        */
    }

    public void processKey(String key) {
        if (myHotKeys.containsKey(key)) {
            String event = myHotKeys.get(key);
            GroovyShell shell = new GroovyShell(myBinding);
            myBinding.setProperty("ID", myUserID);
            Script script = shell.parse(event);
            script.run();
        } else ; //TODO:error
    }

    public void updateScene() {
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(myTriggers);
        script.run();
        executeEntityLogic();
        myLevelManager.updateTimers();
        myLevelManager.updateSequences();
        myCollisionHandler.handleCollisions(myActiveObjects.keySet(), myCollisionResponses);
        myOffset = updateOffset();
    }

    private void executeEntityLogic() {
        GroovyShell shell = new GroovyShell(myBinding);
        for (int entityID : myActiveObjects.keySet()) {
            LogicComponent logicComponent = myEntityManager.getComponent(entityID, LogicComponent.class);
            String logic = logicComponent.getLogic();
            myBinding.setProperty("ID", entityID);
            Script script = shell.parse(logic);
            script.run();
        }
    }

    private double[] updateOffset() {
        BasicComponent basic = (BasicComponent) myActiveObjects.get(myUserID).get(BasicComponent.class);
        double userX = basic.getX();
        double userY = basic.getY();
        double userWidth = basic.getWidth();
        double userHeight = basic.getHeight();
        return myLevelManager.determineOffset(userX, userY, userWidth, userHeight, myScreenWidth, myScreenHeight);
    }

    public double[] getOffset() {
        return myOffset;
    }

    public Map<Integer, Map<Class<? extends Component>, Component>> getEntities() {
        return myActiveObjects;
    }

    public double getScore(int entityID) {
        ScoreComponent score = myEntityManager.getComponent(entityID, ScoreComponent.class);
        return score.getScore();
    }

    public int getUserID(){
        return myUserID;
    }
}
