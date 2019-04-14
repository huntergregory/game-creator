package Engine.src.Triggers;

import java.util.List;

public class TimerSequence {
    List<Timer> mySequence;
    int TimerIndex;
    boolean IsLoop;

    public TimerSequence(List<Timer> sequence, boolean isLoop){
        mySequence = sequence;
        TimerIndex = 0;
        IsLoop = isLoop;
    }

    public void reset(double currentCount){
        TimerIndex = 0;
        mySequence.get(TimerIndex).setCount(currentCount);
    }

    public boolean isLoop() {
        return IsLoop;
    }


    public Timer getCurrentTimer() {
        return mySequence.get(TimerIndex);
    }

    public void setNextTimer(double currentCount) {
        TimerIndex++;
        mySequence.get(TimerIndex).setCount(currentCount);
    }

    public boolean completed() {
        return (TimerIndex == mySequence.size());
    }
}
