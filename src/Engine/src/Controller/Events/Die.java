package Engine.src.Controller.Events;

import Engine.src.Controller.BinderHelper;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.Components.LivesComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Map;
import java.util.Set;

public class Die extends InstanceDependentEvent {

    public Die(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects) {
        super(engineInstances, engineObjects);
    }

    @Override
    protected void modifyInstance(EngineInstance engineInstance, double stepTime, Object... args) {
        if(engineInstance.hasComponent(LivesComponent.class)){
            LivesComponent lives = engineInstance.getComponent(LivesComponent.class);
            lives.removeLife();
            if (lives.expired())
                die(engineInstance);
            else {
                respawn(engineInstance, lives.getRespawnInstructions());
            }
        }
        else
            die(engineInstance);
    }



    private void die(EngineInstance engineInstance){
        myEngineInstances.remove(engineInstance.getID());
        BasicComponent basic = engineInstance.getComponent(BasicComponent.class);
        basic.kill();
    }

    //FIXME - should be another event
    private void respawn(EngineInstance engineInstance, String respawnInstructions){
        Binding binding = new Binding();
        BinderHelper binderHelper = new BinderHelper();
        binderHelper.bindComponentClasses(binding);
        binding.setProperty("instance", engineInstance);
        binding.setProperty("manager", this);
        GroovyShell shell = new GroovyShell(binding);
        Script instructions = shell.parse(respawnInstructions);
        instructions.run();
    }
}
