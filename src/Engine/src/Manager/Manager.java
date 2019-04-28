package Engine.src.Manager;

import Engine.src.Manager.Events.Event;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.Game;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Instance;
import voogasalad.util.reflection.Reflection;
import voogasalad.util.reflection.ReflectionException;

import java.util.List;
import java.util.Map;

/**
 * Combines the old LevelManager and EntityManager
 */
public class Manager {
    private static final String REFLECTION_ERROR = "Event doesn't exist.";
    private static final String CAST_ERROR = "Class created is not an Event.";
    private static final String ILLEGAL_ARGS_ERROR = "Arguments for event did not match.";

    private Game myGame;

    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private double myCount;

    public Manager(Game game, double stepTime) {
        myGame = game;
    }

    public void call(String eventClass, Instance instance, Object ... args) {
        try {
            var event = (Event) Reflection.createInstance(eventClass, myGame);
            event.activate(instance, args);
        }
        catch (ReflectionException e) {
            System.out.println(REFLECTION_ERROR);
        }
        catch (ClassCastException e) {
            System.out.println(CAST_ERROR);
        }
        catch (IllegalArgumentException e) {
            System.out.println(ILLEGAL_ARGS_ERROR);
        }
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
