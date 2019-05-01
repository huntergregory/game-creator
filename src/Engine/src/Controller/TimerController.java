package Engine.src.Controller;

import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerController {
    private GroovyShell myShell;
    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private double myCount;

    public TimerController(GroovyShell shell) {
        myShell = shell;
        myTimers = new HashMap<>();
        myTimerSequences = new ArrayList<>();
        myCount = 0;
    }

    public void addTimer(Timer timer) {
        myTimers.put(0, timer); //FIXME put actual integer in key spot
    }

    public void addTimerSequence(TimerSequence timerSequence) {
        myTimerSequences.add(timerSequence);
    }

    public void update() {
        updateSequences();
        updateTimers();
        myCount++;
    }

    private void updateSequences() {
        for (TimerSequence sequence : myTimerSequences) {
            Timer currentTimer = sequence.getCurrentTimer();
            if (currentTimer.getCount() >= currentTimer.getEndTime()){
                Script script = myShell.parse(currentTimer.getMyEventsAfterTimer());
                script.run();
                sequence.setNextTimer(myCount);
            }
            else {
                Script script = myShell.parse(currentTimer.getStateWhileTimerIsOn());
                script.run();
                currentTimer.increment();
            }
            if (sequence.completed() && sequence.isLoop()) sequence.reset(myCount);
            else myTimers.remove(sequence);
        }
    }

    //FIXME should not be public/open to author
    private void updateTimers() {
        for (int timerID : myTimers.keySet()) {
            Timer timer = myTimers.get(timerID);
            if (timer.getCount() >= timer.getEndTime()){
                Script script = myShell.parse(timer.getMyEventsAfterTimer());
                script.run();
            }
            else {
                Script script = myShell.parse(timer.getStateWhileTimerIsOn());
                script.run();
                timer.increment();
            }
        }
    }

}
