package Engine.src.Controller;

import Engine.src.Controller.Events.Event;
import Engine.src.EngineData.EngineInstance;
import Engine.src.Timers.Timer;
import reflection.Reflection;
import reflection.ReflectionException;

/**
 * Provides a means for the author to not only set the current level to complete, but most importantly, call on any given default Event class within a script. Combines the old LevelManager and EntityManager.
 *
 * @author Hunter Gregory
 */
public class Manager {
    private static final String REFLECTION_ERROR = "Event doesn't exist.";
    private static final String CAST_ERROR = "Class created is not an Event.";
    private static final String ILLEGAL_ARGS_ERROR = "Arguments for event did not match.";
    private static final String EVENTS_FILE_PATH = "Engine.src.Controller.Events.";
    private static final String[] SUBFOLDERS = {"", "AI.", "Aim.", "Health.", "Motion."};

    private EngineParser myParser;
    private TimerController myTimerController;
    boolean levelPassed;
    private double myStepTime;

    /**
     * Create a Manager
     * @param parser
     * @param timerController
     * @param stepTime
     */
    public Manager(EngineParser parser, TimerController timerController, double stepTime) {
        myParser = parser;
        myTimerController = timerController;
        levelPassed = false;
        myStepTime = stepTime;
    }

    /**
     * Use in groovy scripts to call an event with a given name on an EngineInstance. Include any extra parameters that the Event needs.
     * @param eventClass
     * @param engineInstance
     * @param args
     */
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

    /**
     * Get the EngineInstance for the user-defined entity.
     * @return
     * @throws NoInstanceException
     */
    public EngineInstance getUser() throws NoInstanceException {
        var user = myParser.getUserEngineInstance();
        if (user == null)
            throw new NoInstanceException("user");
        return user;
    }

    /**
     * Add a timer to the logic.
     * @param timer
     */
    public void addTimer(Timer timer) {
        myTimerController.addTimer(timer);
    }

    /**
     * Set the level to be completed.
     */
    public void setLevelPass() {
        levelPassed = true;
    }

    /**
     * @return true if the level is passed
     */
    public boolean levelPassed() {
        return levelPassed;
    }
}
