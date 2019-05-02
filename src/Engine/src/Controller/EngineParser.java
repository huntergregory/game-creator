package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.Component;
import Engine.src.EngineData.EngineGameObject;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Timers.Timer;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Scene;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.lang.Sequence;

import java.io.IOException;
import java.util.*;
import java.util.Map;

public class EngineParser {

    private Map<String, String> myHotKeys;
    private Map<Pair<String>, String> myCollisionResponses;
    private String myLevelRules;
    private Set<EngineGameObject> myGameEngineObjects;
    private Map<String, EngineInstance> myEngineInstances;
    private List<Sequence> myTimerSequences;
    private Map<Integer, Timer> myTimers;
    private EngineInstance myUserEngineInstance;

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
        addDefaults();
        parse(game);
    }

    ////// Groovy API   ////////

    public void addCollision(String type1, String type2, String response){
        myCollisionResponses.put(new Pair<>(type1, type2), response);
    }

    public void addKey(String key, String entry) {
        myHotKeys.put(key, entry);
    }

    public void addLevelRules(String rules){
        myLevelRules += rules;
    }


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
        bindComponentClasses(binding);
        String importStatements = getComponentImportStatements();
        for (GameObject serializedObject : serializedObjects) {
            String objectType = serializedObject.objectID;
            EngineGameObject object = new EngineGameObject(objectType);
            binding.setProperty("object", object);
            String objectLogic = importStatements + serializedObject.objectLogic;
            shell.evaluate(objectLogic);
            myGameEngineObjects.add(object);

            makeEngineInstancesOfType(object, serializedInstances, binding, shell);
        }
    }

    private void bindComponentClasses(Binding binding) {
        try {
            var classGrabber = new ClassGrabber();
            for (Class componentClass : classGrabber.getClassesForPackage(Component.class.getPackageName()))
                binding.setProperty(componentClass.getSimpleName(), componentClass);
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Couldn't bind component classes in Groovy. Groovy exception is likely to occur.");
        }
    }

    private String getComponentImportStatements() {
        String result = "";
        try {
            var classGrabber = new ClassGrabber();
            for (Class componentClass : classGrabber.getClassesForPackage(Component.class.getPackageName()))
                result += "import " + componentClass.getName() + ";\n";
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Couldn't bind component classes in Groovy. Groovy exception is likely to occur.");
        }
        return result;
    }

    private void makeEngineInstancesOfType(EngineGameObject object, Set<Instance> serializedInstances, Binding binding, GroovyShell shell) {
        for (Instance serializedInstance : serializedInstances) {
            String instanceOf = serializedInstance.instanceOf;
            String objectType = object.getID();

            if (instanceOf.equals(objectType)) {
                String instanceID = serializedInstance.instanceID;
                EngineInstance engineInstance = object.createInstance(instanceID);
                updateBasicComponent(engineInstance, serializedInstance);
                binding.setProperty("instance", engineInstance);
                String instanceLogic = serializedInstance.instanceLogic;
                Script instanceInitializer = shell.parse(instanceLogic);
                instanceInitializer.run();
                myEngineInstances.put(instanceID, engineInstance);
            }
        }
    }

    private void updateBasicComponent(EngineInstance engineInstance, Instance  instance) {
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
        
    private void addDefaults() {
        myHotKeys.put("D", "manager.call('KeyMoveRight'); ");
        myHotKeys.put("A", "manager.call('KeyMoveLeft'); ");
        myHotKeys.put("W", "manager.call('Jump'); ");
    }

}
