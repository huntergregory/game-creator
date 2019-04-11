package Controller;

import ECS.EntityManager;
import Triggers.Events.Event;
import Triggers.Timer;
import Triggers.TimerSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelManager {
    private boolean levelPassed;
    private List<TimerSequence> myTimers;
    private EntityManager myEntityManager;
    double myCount;
    double myLevelWidth;
    double myLevelHeight;

    public LevelManager(List<TimerSequence> timers, EntityManager entityManager, double count, double width, double height){
        levelPassed = false;
        myEntityManager = entityManager;
        myTimers = timers;
        myCount = count;
        myLevelWidth = width;
        myLevelHeight = height;
    }

    public void addSequence(Map<Integer, List<Event>> eventsWhileOn, Map<Integer, List<Event>> eventsAfter,
                            Map<Integer, Double> durations, boolean isLoop) {
        List<Timer> timerList = new ArrayList<>();
        for(Integer key: eventsWhileOn.keySet()){
            timerList.add(new Timer(eventsWhileOn.get(key), eventsAfter.get(key), durations.get(key)));
        }
        myTimers.add(new TimerSequence(timerList, isLoop));
    }

    public void updateTimer() {
        for (TimerSequence sequence : myTimers) {
            Timer currentTimer = sequence.getCurrentTimer();
            if (currentTimer.getCount() >= currentTimer.getEndTime()){
                currentTimer.activateEvents(currentTimer.getMyEventsAfterTimer(), myEntityManager, this);
                sequence.setNextTimer(myCount);
            }
            else {
                currentTimer.activateEvents(currentTimer.getEventsWhileOn(), myEntityManager, this);
                currentTimer.increment();
            }
            if (sequence.completed() && sequence.isLoop()) sequence.reset(myCount);
            else myTimers.remove(sequence);
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
}
