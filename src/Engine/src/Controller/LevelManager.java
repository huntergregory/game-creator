package Engine.src.Controller;

import Engine.src.ECS.EntityManager;
import Engine.src.Manager.Manager;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.GameObjects.Components.LogicComponent;
import gamedata.GameObjects.Instance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.List;
import java.util.Map;

@Deprecated
public class LevelManager {
    private boolean levelPassed;
    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private Manager myManager;
    double myCount;
    double myLevelWidth;
    double myLevelHeight;

    public LevelManager(Map<Integer, Timer> timers, List<TimerSequence> timerSequences, Manager manager, double count, double width, double height){
        levelPassed = false;
        myManager = manager;
        myTimers = timers;
        myCount = count;
        myLevelWidth = width;
        myLevelHeight = height;
        myTimerSequences = timerSequences;
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
