package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.Timers.Timer;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;
import java.util.Map;

public class EngineParser {

    private Map<String, String> myHotKeys;
    private Map<Pair<String>, String> myCollisionResponses;
    private String myLevelRules;

    public EngineParser(String levelRules, Map collisionResponses, Map hotKeys, List timerSequences, Map timers){
        myLevelRules = levelRules;
        myCollisionResponses = collisionResponses;
        myHotKeys = hotKeys;
        myTimerSequences = timerSequences;
        myTimers = timers;
    }

    public void initializeDataTypes(String sceneLogic){
        Binding binding = new Binding();
        binding.setProperty("parser", this);
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(sceneLogic);
        script.run();
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
