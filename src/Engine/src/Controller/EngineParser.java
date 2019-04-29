package Engine.src.Controller;

import Engine.src.ECS.Pair;
import Engine.src.Timers.TimerSequence;
import gamedata.GameObjects.Components.Component;
import Engine.src.Timers.Timer;
import gamedata.GameObjects.Instance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.*;
import java.util.Map;
import java.util.Set;

public class EngineParser {

    private Map<String, String> myHotKeys;
    private Map<Pair<String>, String> myCollisionResponses;
    private Set<Instance> myInstances;
    private List<TimerSequence> myTimerSequences;
    private Map<Integer, Timer> myTimers;

    public EngineParser(){
        myInstances = new HashSet<>();
        myCollisionResponses = new HashMap<>();
        myHotKeys = new HashMap<>();
        myTimerSequences = new ArrayList<>();
        myTimers = new HashMap<>();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void initializeDataTypes(String sceneLogic){
        System.out.println(sceneLogic);
        Binding binding = new Binding();
        binding.setProperty("parser", this);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(sceneLogic);
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

    private void addKey() {
        //TODO
    }

}
