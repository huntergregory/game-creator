package Engine.src.Manager;

import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.LivesComponent;
import gamedata.Scene;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.List;
import java.util.Map;

/**
 * Combines the old LevelManager and EntityManager
 */
public class Manager {
    private Map<Integer, Map<Class<? extends Component>, Component>> myEntityMap;

    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private double myCount;

    public Manager(Map<Integer, Map<Class<? extends Component>, Component>> entityMap, double stepTime) {
        myEntityMap = entityMap;
    }

    public void call(String eventClass, String instanceID, Object ... args) {
        try {

        }
        catch (
    }

    public void addTimer(String eventsWhileOn, String eventsAfter, double duration) {
        int max = 0;
        for(int ID : myTimers.keySet()){
            if (ID > max) max = ID;
        }
        myTimers.put(max + 1, new Timer(eventsWhileOn, eventsAfter, duration, myCount));
    }

    //FIXME should not be public/open to author
    public void updateSequences() {
        for (TimerSequence sequence : myTimerSequences) {
            Timer currentTimer = sequence.getCurrentTimer();
            if (currentTimer.getCount() >= currentTimer.getEndTime()){
                currentTimer.activateEvents(currentTimer.getMyEventsAfterTimer(), myEntityManager, this);
                sequence.setNextTimer(myCount);
            }
            else {
                currentTimer.activateEvents(currentTimer.getStateWhileTimerIsOn(), myEntityManager, this);
                currentTimer.increment();
            }
            if (sequence.completed() && sequence.isLoop()) sequence.reset(myCount);
            else myTimers.remove(sequence);
        }
    }

    //FIXME should not be public/open to author
    public void updateTimers() {
        for (int timerID : myTimers.keySet()) {
            Timer timer = myTimers.get(timerID);
            if (timer.getCount() >= timer.getEndTime()){
                timer.activateEvents(timer.getMyEventsAfterTimer(), this);
            }
            else {
                timer.activateEvents(timer.getStateWhileTimerIsOn(), this);
                timer.increment();
            }
        }
    }
}
