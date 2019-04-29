package Engine.src.Manager;

import Engine.src.EngineData.Components.LogicComponent;
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
    private static final String[] SUBFOLDERS = {"", "AI", "Aim", "Health"};

    private Game myGame;
    private double myStepTime;

    private Map<Integer, Timer> myTimers;
    private List<TimerSequence> myTimerSequences;
    private double myCount;
    private Binding myBinding;
    private GroovyShell myShell;
    boolean levelPassed;
/*
    public static void main(String[] args) {
        var defaultEnvironment = new EnvironmentComponent(0,0,0,0);
        var basic = new BasicComponent("", 50, 50, 100, 100);
        var motion = new MotionComponent(5, 5, 5,5, defaultEnvironment);
        var health = new HealthComponent(1, 5);
        var mario = new EngineGameObject("mario");
        mario.addComponent(basic, motion, health);
        var block = new EngineGameObject("block");
        block.addComponent(basic);
        var instance1 = mario.createInstance("mario1");
        var instance2 = block.createInstance("block1");

        HashSet<EngineInstance> instances = new HashSet<>();
        instances.add(instance1);
        instances.add(instance2);
        var scene = new Scene();
        scene.instances = instances;
        var game = new Game();
        game.currentScene = scene;

        var manager = new Manager(game, 0, new Binding());
        manager.call("AddToHealth", instance1, 2);
    }
*/
    public Manager(Game game, double stepTime, Binding binding) {
        myGame = game;
        myStepTime = stepTime;
        myBinding = binding;
        myShell = new GroovyShell(myBinding);
        levelPassed = false;
    }

    public void call(String eventClass, EngineInstance engineInstance, Object ... args) {
        for(String subfolder : SUBFOLDERS) {
            try {
                var event = (Event) Reflection.createInstance(EVENTS_FILE_PATH + subfolder + eventClass, getCurrentInstances());
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

    public void updateCount(){
        myCount++;
    }

    public void updateSequences() {
        for (TimerSequence sequence : myTimerSequences) {
            Timer currentTimer = sequence.getCurrentTimer();
            if (currentTimer.getCount() >= currentTimer.getEndTime()){
                Script script = myShell.parse(currentTimer.getMyEventsAfterTimer());
                script.run();
                sequence.setNextTimer(myCount);
            }
            else {
                Script script = myShell.parse(currentTimer.getStateWhileTimerIsOn());
                script.run();
                currentTimer.increment();
            }
            if (sequence.completed() && sequence.isLoop()) sequence.reset(myCount);
            else myTimers.remove(sequence);
        }
    }

    //FIXME should not be public/open to author
    public void updateTimers() {
        for (int timerID : myTimers.keySet()) {
            Timer timer = myTimers.get(timerID);
            if (timer.getCount() >= timer.getEndTime()){
                Script script = myShell.parse(timer.getMyEventsAfterTimer());
                script.run();
            }
            else {
                Script script = myShell.parse(timer.getStateWhileTimerIsOn());
                script.run();
                timer.increment();
            }
        }
    }

    public void executeEntityLogic() {
        for (EngineInstance engineInstance : getCurrentInstances()) {
            LogicComponent logicComponent = engineInstance.getComponent(LogicComponent.class);
            String logic = logicComponent.getLogic();
            myBinding.setProperty("engineInstance", engineInstance);
            Script script = myShell.parse(logic);
            script.run();
        }
    }


    public double getMyStepTime() {
        return myStepTime;
    }

    public void setLevelPass() {
        levelPassed = true;
    }

    public boolean levelPassed() {
        return levelPassed;
    }

    private Set<EngineInstance> getCurrentInstances() {
        return myGame.scenes.get(myGame.currentLevel).instances;
    }
}
