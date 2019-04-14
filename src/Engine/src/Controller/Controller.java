package Engine.src.Controller;

import Engine.src.Triggers.Conditionals.Conditional;
import Engine.src.Triggers.Conditionals.HealthComparison;
import Engine.src.ECS.CollisionDetector;
import Engine.src.Components.BasicComponent;
import Engine.src.Components.Component;
import Engine.src.Components.HealthComponent;
import Engine.src.Components.TagsComponent;
import Engine.src.ECS.EntityManager;
import Engine.src.ECS.Pair;
import Engine.src.Triggers.Events.ObjectEvents.Jump;
import Engine.src.Triggers.Events.ObjectEvents.MoveLeft;
import Engine.src.Triggers.Events.ObjectEvents.ObjectEvent;
import Engine.src.Triggers.Events.GameEvents.GameEvent;
import Engine.src.Triggers.Events.ObjectEvents.*;
import Engine.src.ECS.CollisionHandler;
import Engine.src.Triggers.Events.Event;
import Engine.src.Triggers.TimerSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private Map<String, Event> myHotKeys;
    private List<TimerSequence> myTimers;
    private Map<Pair<String>, Pair<List<Event>>> myCollisionResponses;
    private Map<Integer, Map<Class<? extends Component>, Component>> myActiveObjects;
    private List<Event> myTriggers;

    private int myUserID;
    private double myStepTime;
    private double myIterationCounter;
    private double[] myOffset;

    private CollisionHandler myCollisionHandler;
    private EntityManager myEntityManager;
    private LevelManager myLevelManager;

    public Controller(double stepTime, double screenWidth, double screenHeight, double levelWidth, double levelHeight){
        myHotKeys = new HashMap<>();
        myTimers = new ArrayList<>();
        myCollisionResponses = new HashMap<>();
        myTriggers = new ArrayList<>();

        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        //myDataManager = new DataManager();
        initializeDataVariables();
        myLevelManager = new LevelManager(myTimers, myEntityManager, myIterationCounter, levelWidth, levelHeight);
        myEntityManager = new EntityManager(myActiveObjects, myStepTime);
        myCollisionHandler = new CollisionHandler(myEntityManager, myLevelManager, new CollisionDetector(myEntityManager));
        myIterationCounter = 0;

        setDefaultKeys();
        setDefaultTriggers();
        myOffset = updateOffset();
    }

    //FIXME??
    public void initializeDataVariables(){
        myActiveObjects = new DefaultGame().getActiveObjects(); //FIXME remove for non default, hardcoded game
        myCollisionResponses = new DefaultGame().getCollisionMap();
        //myActiveObjects = myDataManager.loadDefaultObjects();
        //myHotKeys = myDataManager.loadHotKeyMap();
        //myCollisionResponses = myDataManager.loadCollisionResponseMap();
        //myTimers = myDataManager.loadTimerMap();
        //myTriggers = myDataManager.loadTriggers();
        for(int id : myActiveObjects.keySet()){
            Component type =  myActiveObjects.get(id).get(TagsComponent.class);
            if(((TagsComponent) type).contains("USER")){
                myUserID = id;
                break;
            }
        }
    }

    private void setDefaultKeys() {
        myHotKeys.put("A", new MoveLeft(myUserID));
        myHotKeys.put("D", new MoveRight(myUserID));
        myHotKeys.put("SPACE", new Jump(myUserID));
    }

    private void setDefaultTriggers(){
        for(Integer id : myActiveObjects.keySet()) {
            Component health = myEntityManager.getComponent(id, HealthComponent.class);
            if (health != null) {
                List<Conditional> conditionals = new ArrayList<>();
                conditionals.add(new HealthComparison(true, id, "<=", new HealthComponent(0, 0)));
                myTriggers.add(new Die(conditionals, id));
            }
        }
    }

    public void processKey(String key){
        if (myHotKeys.containsKey(key)){
            Event event = myHotKeys.get(key);
            //event = event.copy();
            event.setConditionalObject(myUserID);
            if (event.conditionsSatisfied(myEntityManager)){
                if(event instanceof ObjectEvent){
                    //((ObjectEvent) event).setEventObject(myUserID);
                    ((ObjectEvent) event).activate(myEntityManager);
                }
            }
        }
        else; //TODO:error
    }

    public void updateScene(){
        List<Event> newTriggers = new ArrayList<>();
        for( Event event : myTriggers){
            if (event.conditionsSatisfied(myEntityManager)){
                if (event instanceof ObjectEvent) ((ObjectEvent) event).activate(myEntityManager);
                else if (event instanceof GameEvent) ((GameEvent) event).activate(myLevelManager);
                }
            else newTriggers.add(event);
            }
        myTriggers = newTriggers;
        myLevelManager.updateTimer();
        myCollisionHandler.dealWithCollisions(myActiveObjects.keySet(), myCollisionResponses);
        myOffset = updateOffset();
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
    public Map<Integer, Map<Class<? extends Component>, Component>> getEntities(){
        return myActiveObjects;
    }

}
