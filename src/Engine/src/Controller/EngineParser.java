package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.EngineData.EngineGameObject;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Timers.Timer;
import gamedata.GameObject;
import gamedata.Instance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.lang.Sequence;

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

    public EngineParser(String levelRules, Map collisionResponses, Map hotKeys, List timerSequences, Map timers){
        myLevelRules = levelRules;
        myCollisionResponses = collisionResponses;
        myHotKeys = hotKeys;
        myTimerSequences = timerSequences;
        myTimers = timers;

        myEngineInstances = new HashSet<>();
        myGameEngineObjects = new HashSet<>();
    }

    public void parse(String sceneLogic, List<GameObject> serializedObjects, Set<Instance> serializedInstances){
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        binding.setProperty("parser", this);

        for(GameObject serializedObject : serializedObjects){
            String objectType = serializedObject.objectID;
            EngineGameObject object = new EngineGameObject(objectType);
            binding.setProperty("object", object);
            String objectLogic = serializedObject.objectLogic;
            Script objectInitialzer = shell.parse(objectLogic);
            objectInitialzer.run();
            myGameEngineObjects.add(object);

            for(Instance serializedInstance : serializedInstances){
                String instanceOf = serializedInstance.instanceOf;

                if (instanceOf.equals(objectType)) {
                    object = new EngineGameObject(objectType);
                    String instanceID = serializedInstance.instanceID;
                    EngineInstance instance = object.createInstance(instanceID);
                    binding.setProperty("instance", instance);
                    String instanceLogic = serializedInstance.instanceLogic;
                    Script instanceInitializer = shell.parse(instanceLogic);
                    instanceInitializer.run();
                    myEngineInstances.add(instance);
                }
            }
            Script dataInitializer = shell.parse(sceneLogic);
            dataInitializer.run();
        }
    }


    private void addTimer(String eventsWhileOn, String eventsAfter, double duration) {
        int max = 0;
        for(int ID : myTimers.keySet()){
            if (ID > max) max = ID;
        }
        myTimers.put(max + 1, new Timer(eventsWhileOn, eventsAfter, duration, 0));
    }
    
    public void addCollision(String type1, String type2, String response){
        myCollisionResponses.put(new Pair<>(type1, type2), response);
    }

    private void addKey(String key, String entry) {
        myHotKeys.put(key, entry);
    }

    private void addLevelRules(String rules){
        myLevelRules += rules;
    }

}
