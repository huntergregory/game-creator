package Engine.src.Controller;

import Engine.src.Controller.Events.Event;
import Engine.src.Controller.TimerController;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.LogicComponent;
import Engine.src.EngineData.Components.MotionComponent;
import Engine.src.Controller.Events.Event;
import Engine.src.Timers.Timer;
import Engine.src.Timers.TimerSequence;
import gamedata.Game;
import Engine.src.EngineData.EngineInstance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import reflection.Reflection;
import reflection.ReflectionException;

import javax.swing.text.html.parser.Parser;
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
    private static final String EVENTS_FILE_PATH = "Engine.src.Controller.Events.";
    private static final String[] SUBFOLDERS = {"", "AI.", "Aim.", "Health.", "Motion."};

    private EngineParser myParser;
    private TimerController myTimerController;
    private boolean levelPassed;
    private double myStepTime;

    public Manager(EngineParser parser, TimerController timerController, double stepTime) {
        myParser = parser;
        myTimerController = timerController;
        levelPassed = false;
        myStepTime = stepTime;
    }

    public void call(String eventClass, EngineInstance engineInstance, Object ... args) {
        try {
            for(String subfolder : SUBFOLDERS) {
                String className = EVENTS_FILE_PATH + subfolder + eventClass;
                if (isClass(className)) {
                    var event = (Event) Reflection.createInstance(className, myParser.getEngineInstances(), myParser.getEngineGameObjects());
                    event.activate(engineInstance, myStepTime, args);
                    break;
                }
            }
        }
        catch (ReflectionException e) {
            System.out.println(REFLECTION_ERROR);
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println(CAST_ERROR);
        }
        catch (IllegalArgumentException e) {
            System.out.println(ILLEGAL_ARGS_ERROR);
        }
    }

    private boolean isClass(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    public EngineInstance getUser() throws NoInstanceException {
        var user = myParser.getUserEngineInstance();
        if (user == null)
            throw new NoInstanceException("user");
        return user;
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
