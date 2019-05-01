package Engine.src.Manager;

import Engine.src.Controller.TimerController;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.Manager.Events.Event;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.Game;
import Engine.src.EngineData.EngineInstance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import voogasalad.util.reflection.Reflection;
import voogasalad.util.reflection.ReflectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Combines the old LevelManager and EntityManager
 */
public class Manager {
    private static final String REFLECTION_ERROR = "Event doesn't exist.";
    private static final String CAST_ERROR = "Class created is not an Event.";
    private static final String ILLEGAL_ARGS_ERROR = "Arguments for event did not match.";
    private static final String EVENTS_FILE_PATH = "Engine.src.Manager.Events.";
    private static final String[] SUBFOLDERS = {"", "AI", "Aim", "Health", "Motion"};

    private Set<EngineInstance> myEngineInstances;
    private TimerController myTimerController;
    boolean levelPassed;

    public Manager(Set<EngineInstance> engineInstances, TimerController timerController) {
        myEngineInstances = engineInstances;
        myTimerController = timerController;
        levelPassed = false;
    }

    public void call(String eventClass, EngineInstance engineInstance, Object ... args) {
        for(String subfolder : SUBFOLDERS) {
            try {
                var event = (Event) Reflection.createInstance(EVENTS_FILE_PATH + subfolder + "." + eventClass, myEngineInstances);
                event.activate(engineInstance, args);
            } catch (ReflectionException e) {
                System.out.println(REFLECTION_ERROR);
            } catch (ClassCastException e) {
                System.out.println(CAST_ERROR);
            } catch (IllegalArgumentException e) {
                System.out.println(ILLEGAL_ARGS_ERROR);
            }
        }
    }

    public void addTimer(Timer timer) {
        myTimerController.addTimer(timer);
    }

    public void setLevelPass() {
        levelPassed = true;
    }

    public boolean levelPassed() {
        return levelPassed;
    }
}
