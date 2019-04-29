package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;
import java.util.Map;

public class EngineParser {

    private Map<String, String> myHotKeys;
    private Map<Pair<String>, String> myCollisionResponses;
    private String myLevelRules;
    EngineInstance myUserEngineInstance;
    Set<EngineInstance> myEngineInstances;
    private List<TimerSequence> myTimerSequences;
    private HashMap<Integer, Timer> myTimers;

    public EngineParser() {
        myHotKeys = new HashMap<>();
        myCollisionResponses = new HashMap<>();
        myLevelRules = "";
        myTimerSequences = new ArrayList<>();
        myTimers = new HashMap<>();
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

    private void addTimer(String eventsWhileOn, String eventsAfter, double duration) {
        int max = 0;
        for(int ID : myTimers.keySet()){
            if (ID > max) max = ID;
        }
        myTimers.put(max + 1, new Timer(eventsWhileOn, eventsAfter, duration, 0));
    }

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
}
