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
    private Set<EngineInstance> myEngineInstances;
    private List<Sequence> myTimerSequences;
    private Map<Integer, Timer> myTimers;
    private EngineInstance myUserEngineInstance;

    protected EngineParser(Game game) {
        myLevelRules = "";
        myCollisionResponses = new HashMap<>();
        myHotKeys = new HashMap<>();
        myTimerSequences = new ArrayList<>();
        myTimers = new HashMap<>();

        myEngineInstances = new HashSet<>();
        myGameEngineObjects = new HashSet<>();
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

    protected Set<EngineInstance> getEngineInstances() {
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

        bindComponents(binding);
        initEngineObjectsAndInstances(serializedObjects, serializedInstances, binding, shell);

        binding.setProperty("parser", this);
        shell.evaluate(sceneLogic);
        setUser();
    }

    private void bindComponents(Binding binding) {
        try {
            for (Class componentClass : new ClassGrabber().getClassesForPackage(Component.class.getPackageName()))
                binding.setProperty(componentClass.getSimpleName(), componentClass);
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Couldn't bind component classes in Groovy. Groovy exception is likely to occur.");
        }
    }


    private void initEngineObjectsAndInstances(List<GameObject> serializedObjects, Set<Instance> serializedInstances, Binding binding, GroovyShell shell) {
        for (GameObject serializedObject : serializedObjects) {
            String objectType = serializedObject.objectID;
            EngineGameObject object = new EngineGameObject(objectType);
            binding.setProperty("object", object);
            String objectLogic = serializedObject.objectLogic;
            Script objectInitializer = shell.parse(objectLogic);
            objectInitializer.run();
            myGameEngineObjects.add(object);

            makeEngineInstancesOfType(serializedInstances, binding, shell, objectType);
        }
    }

    private void makeEngineInstancesOfType(Set<Instance> serializedInstances, Binding binding, GroovyShell shell, String objectType) {
        EngineGameObject object;
        for (Instance serializedInstance : serializedInstances) {
            String instanceOf = serializedInstance.instanceOf;

            if (instanceOf.equals(objectType)) {
                object = new EngineGameObject(objectType);
                String instanceID = serializedInstance.instanceID;
                EngineInstance engineInstance = object.createInstance(instanceID);
                updateBasicComponent(engineInstance, serializedInstance);
                binding.setProperty("instance", engineInstance);
                String instanceLogic = serializedInstance.instanceLogic;
                Script instanceInitializer = shell.parse(instanceLogic);
                instanceInitializer.run();
                myEngineInstances.add(engineInstance);
            }
        }
    }

    private void updateBasicComponent(EngineInstance engineInstance, Instance  instance) {
        var basic = new BasicComponent(instance.bgImage, instance.x, instance.y, instance.width, instance.height, instance.zIndex);
        engineInstance.addComponent(basic);
    }

    private void setUser() {
        for (EngineInstance engineInstance : myEngineInstances) {
            String type = engineInstance.getType();
            if (type.equals("User")) {
                myUserEngineInstance = engineInstance;
                break;
            }
        }
    }

}
