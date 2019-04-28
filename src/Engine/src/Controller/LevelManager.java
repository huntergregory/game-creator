package Engine.src.Controller;

import Engine.src.ECS.EntityManager;
import Engine.src.Triggers.Events.Event;
import Engine.src.Triggers.Timer;
import Engine.src.Triggers.TimerSequence;

import java.util.List;
import java.util.Map;

public class LevelManager {
    private boolean levelPassed;
    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private EntityManager myEntityManager;
    double myCount;
    double myLevelWidth;
    double myLevelHeight;

    public LevelManager(Map<Integer, Timer> timers, List<TimerSequence> timerSequences, EntityManager entityManager, double count, double width, double height){
        levelPassed = false;
        myEntityManager = entityManager;
        myTimers = timers;
        myCount = count;
        myLevelWidth = width;
        myLevelHeight = height;
        myTimerSequences = timerSequences;
    }

    public void addTimer(String eventsWhileOn, String eventsAfter, double duration) {
        int max = 0;
        for(int ID : myTimers.keySet()){
            if (ID > max) max = ID;
        }
        myTimers.put(max + 1, new Timer(eventsWhileOn, eventsAfter, duration, myCount));
    }

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

    public void updateTimers() {
        for (int timerID : myTimers.keySet()) {
            Timer timer = myTimers.get(timerID);
            if (timer.getCount() >= timer.getEndTime()){
                timer.activateEvents(timer.getMyEventsAfterTimer(), myEntityManager, this);
            }
            else {
                timer.activateEvents(timer.getStateWhileTimerIsOn(), myEntityManager, this);
                timer.increment();
            }
        }
    }


    public void setLevelPass() {
        levelPassed = true;
    }

    public double[] determineOffset(double userX, double userY, double userWidth, double userHeight, double screenWidth,
                                    double screenHeight) {
        double offsetX;
        double offsetY;

        if (userX <= .5 * screenWidth - .5 * userWidth) {
            offsetX = 0;
        }
        /*else if (myLevelWidth - userX <= .5 * screenWidth + .5 * userWidth) {
            offsetX = myLevelWidth - screenWidth;
        }*/ //FIXME restricting max scroll to very small even when level width is large...
        else {
            offsetX = userX + .5 * userWidth - .5 * screenWidth;
        }

        if (userY <= .5 * screenHeight - .5 * userHeight) {
            offsetY = 0;
        }
        else if (myLevelHeight - userY <= .5 * screenHeight + .5 * userHeight) {
            offsetY = myLevelHeight - screenHeight;
        }
        else {
            offsetY = userY + .5 * userHeight - .75 * screenHeight; // this puts the user 3/4 the way dow the screen
        }

        return new double[]{offsetX, 0}; //FIXME hardcoding 0 offset in y direction for demo
    }

    public void addSequence(Map<Integer, List<Event>> myEventsWhileOn, Map<Integer, List<Event>> myEventsAfter, Map<Integer, Double> myDurations, boolean isLoop) {
    }
}
