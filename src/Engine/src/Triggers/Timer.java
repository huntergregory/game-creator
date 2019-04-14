package Engine.src.Triggers;

import Engine.src.ECS.EntityManager;
import Engine.src.Controller.LevelManager;
import Engine.src.Triggers.Events.Event;
import Engine.src.Triggers.Events.GameEvents.GameEvent;
import Engine.src.Triggers.Events.ObjectEvents.ObjectEvent;

import java.util.List;

public class Timer {
    double myCount;
    double myDuration;
    double myStartTime;
    List<Event> myEventsWhileOn;
    List<Event> myEventsAfterTimer;

    public Timer(List<Event> eventsWhileOn, List<Event> eventsAfterTimer, double duration){
        myCount = 0;
        myStartTime = 0;
        myDuration = duration;
        myEventsWhileOn = eventsWhileOn;
        myEventsAfterTimer = eventsAfterTimer;
    }

    public Timer(List<Event> eventsWhileOn, List<Event> eventsAfterTimer, double duration, double currentCount){
        myCount = currentCount;
        myStartTime = currentCount;
        myDuration = duration;
        myEventsWhileOn = eventsWhileOn;
        myEventsAfterTimer = eventsAfterTimer;
    }

    public void activateEvents(List<Event> events, EntityManager entityManager, LevelManager levelManager) {
        for (Event e : events) {
            Event event = e.copy();
            if (event.conditionsSatisfied(entityManager)) {
                if (event instanceof ObjectEvent) ((ObjectEvent) event).activate(entityManager);
                else if (event instanceof GameEvent) ((GameEvent) event).activate(levelManager);
            }

        }
    }

    public void setCount(double currentCount){myCount = currentCount;}

    public void increment(){
        myCount++;
    }

    public double getEndTime() {
        return myStartTime + myDuration;
    }

    public void reset(){
        myStartTime = myCount;
    }

    public double getCount() {
        return myCount;
    }

    public List<Event> getEventsWhileOn(){
        return myEventsWhileOn;
    }

    public List<Event> getMyEventsAfterTimer(){
        return myEventsAfterTimer;
    }

}
