package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.EngineGameObject;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;
import Engine.src.Timers.Timer;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Scene;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.lang.Sequence;

import java.util.*;

/**
 * Converts String game data used in the author into data types of the engine (some of which - the components - are shared with the player)
 *
 * @author Daniel Kingsbury
 * @author Hunter Gregory
 */
public class EngineParser {

    private Map<String, String> myHotKeys;
    private Map<Pair<String>, String> myCollisionResponses;
    private String myLevelRules;
    private Set<EngineGameObject> myGameEngineObjects;
    private Map<String, EngineInstance> myEngineInstances;
    private List<Sequence> myTimerSequences;
    private Map<Integer, Timer> myTimers;
    private EngineInstance myUserEngineInstance;
    private BinderHelper myBinderHelper;

    private boolean scrollingHoriz;
    private boolean scrollingVert;

    protected EngineParser(Game game) {
        myLevelRules = "";
        myCollisionResponses = new HashMap<>();
        myHotKeys = new HashMap<>();
        myTimerSequences = new ArrayList<>();
        myTimers = new HashMap<>();

        // scrolling in both directions by default (can be overriden with script)
        scrollingHoriz = true;
        scrollingVert = true;

        myEngineInstances = new HashMap<>();
        myGameEngineObjects = new HashSet<>();
        myBinderHelper = new BinderHelper();
        parse(game);
    }

    ////// Groovy API   ////////

    /**
     * Add a groovy script response for a collision between two different object types. The keywords for the types are respectively, object1 and object2
     * @param type1
     * @param type2
     * @param response
     */
    public void addCollision(String type1, String type2, String response){
        myCollisionResponses.put(new Pair<>(type1, type2), response);
    }

    /**
     * Add a groovy script response that affects the user for a key input. The keyword is user.
     * @param key
     * @param entry
     */
    public void addKey(String key, String entry) {
        myHotKeys.put(key, entry);
    }

    /**
     * Add groovy scripts that run every game loop
     * @param rules
     */
    public void addLevelRules(String rules){
        myLevelRules += rules;
    }


    /**
     * Add a timer that has groovy-scripted events while on, groovy-scripted events to be done after, and a duration.
     * @param eventsWhileOn
     * @param eventsAfter
     * @param duration
     */
    public void addTimer(String eventsWhileOn, String eventsAfter, double duration) {
        int max = 0;
        for(int ID : myTimers.keySet()){
            if (ID > max) max = ID;
        }
        myTimers.put(max + 1, new Timer(eventsWhileOn, eventsAfter, duration, 0));
    }


    ////// Getters for Engine ///////

    protected Map<String, String> getHotKeys() {
        return myHotKeys;
    }

    protected Map<Pair<String>, String> getCollisions() {
        return myCollisionResponses;
    }

    protected String getLevelRules() {
        return myLevelRules;
    }

    protected Map<String, EngineInstance> getEngineInstances() {
        return myEngineInstances;
    }

    protected Set<UnmodifiableEngineGameObject> getEngineGameObjects() {
        HashSet<UnmodifiableEngineGameObject> set = new HashSet<>();
        for (EngineGameObject engineGameObject : myGameEngineObjects)
            set.add(new UnmodifiableEngineGameObject(engineGameObject));
        return set;
    }

    protected EngineInstance getUserEngineInstance() {
        return myUserEngineInstance;
    }


    private void parse(Game game) {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);

        Integer levelIndex = game.currentLevel;
        Scene scene = game.scenes.get(levelIndex);
        Set<Instance> serializedInstances = scene.instances;
        List<GameObject> serializedObjects = game.gameObjects;
        String sceneLogic = scene.sceneLogic;

        initEngineObjectsAndInstances(serializedObjects, serializedInstances, binding, shell);

        binding.setProperty("parser", this);
        shell.evaluate(sceneLogic);
        setUser();
    }


    private void initEngineObjectsAndInstances(List<GameObject> serializedObjects, Set<Instance> serializedInstances, Binding binding, GroovyShell shell) {
        myBinderHelper.bindComponentClasses(binding);
        String importStatements = myBinderHelper.getComponentImportStatements();
        for (GameObject serializedObject : serializedObjects) {
            String objectType = serializedObject.objectID;
            EngineGameObject object = new EngineGameObject(objectType);
            binding.setProperty("object", object);
            String objectLogic = importStatements + serializedObject.objectLogic;
            shell.evaluate(objectLogic);
            myGameEngineObjects.add(object);
            makeEngineInstancesOfType(object, serializedInstances, binding, shell, importStatements);
        }
    }


    private void makeEngineInstancesOfType(EngineGameObject object, Set<Instance> serializedInstances, Binding binding, GroovyShell shell, String importStatements) {
        for (Instance serializedInstance : serializedInstances) {
            String instanceOf = serializedInstance.instanceOf;
            String objectType = object.getID();

            if (instanceOf.equals(objectType)) {
                String instanceID = serializedInstance.instanceID;
                EngineInstance engineInstance = object.createInstance(instanceID);
                updateBasicComponent(engineInstance, serializedInstance);
                binding.setProperty("instance", engineInstance);
                String instanceLogic = importStatements + serializedInstance.instanceLogic;
                Script instanceInitializer = shell.parse(instanceLogic);
                instanceInitializer.run();
                myEngineInstances.put(instanceID, engineInstance);
            }
        }
    }

    private void updateBasicComponent(EngineInstance engineInstance, Instance instance) {
        var basic = new BasicComponent(instance.bgImage, Double.toString(instance.x), Double.toString(instance.y), Double.toString(instance.width), Double.toString(instance.height), Integer.toString(instance.zIndex));
        engineInstance.addComponent(basic);
    }

    private void setUser() {
        for (String id : myEngineInstances.keySet()) {
            EngineInstance engineInstance = myEngineInstances.get(id);
            String type = engineInstance.getType();
            if (type.equals("user")) {
                myUserEngineInstance = engineInstance;
                break;
            }
        }
    }

    private void addDefaults() {
        myHotKeys.put("D", "manager.call('KeyMoveRight'); ");
        myHotKeys.put("A", "manager.call('KeyMoveLeft'); ");
        myHotKeys.put("W", "manager.call('Jump'); ");
    }

    public void setScrolling(boolean horiz, boolean vert) {
        scrollingHoriz = horiz;
        scrollingVert = vert;
    }

    public boolean getHorizScrolling() {
        return scrollingHoriz;
    }

    public boolean getVertScrolling() {
        return scrollingVert;
    }

}
