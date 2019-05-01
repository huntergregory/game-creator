package Engine.src.Timers;

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
