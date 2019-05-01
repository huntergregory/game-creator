package Engine.src.Triggers;

import Engine.src.Controller.LevelManager;
import Engine.src.ECS.EntityManager;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class Timer {
    double myCount;
    double myDuration;
    double myStartTime;
    String myStateWhileTimerIsOn;
    String myEventsAfterTimer;

    public Timer(String stateWhileOn, String eventsAfterTimer, double duration){
        myCount = 0;
        myStartTime = 0;
        myDuration = duration;
        myStateWhileTimerIsOn = stateWhileOn;
        myEventsAfterTimer = eventsAfterTimer;
    }

    public Timer(String eventsWhileOn, String eventsAfterTimer, double duration, double currentCount){
        myCount = currentCount;
        myStartTime = currentCount;
        myDuration = duration;
        myStateWhileTimerIsOn = eventsWhileOn;
        myEventsAfterTimer = eventsAfterTimer;
    }

    public void activateEvents(String events, EntityManager entityManager, LevelManager levelManager) {
        Binding managerSetter = new Binding();
        GroovyShell shell = new GroovyShell();
        managerSetter.setProperty("entityManager", entityManager);
        managerSetter.setProperty("levelManager", levelManager);
        Script script = shell.parse(events);
        script.run();
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

    public String getStateWhileTimerIsOn(){
        return myStateWhileTimerIsOn;
    }

    public String getMyEventsAfterTimer(){
        return myEventsAfterTimer;
    }

}
