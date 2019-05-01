package Engine.src.Manager.Events;

import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.Components.LivesComponent;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Set;

//FIXME
public class Die extends InstanceDependentEvent {

    public Die(Set<EngineInstance> engineInstances) {
        super(engineInstances);
    }

    @Override
    protected void modifyInstance(EngineInstance engineInstance, double stepTime, Object... args) {

        if(engineInstance.hasComponent(LivesComponent.class)){
            LivesComponent lives = engineInstance.getComponent(LivesComponent.class);
            if (lives.expired())
                myEngineInstances.remove(engineInstance);
            else {
                respawn(engineInstance, lives.getRespawnInstructions());
                lives.removeLife();
            }
        }
        else
            myEngineInstances.remove(engineInstance);
    }

    //FIXME - should be another event
    private void respawn(EngineInstance engineInstance, String respawnInstructions){
        Binding binding = new Binding();
        binding.setProperty("engineInstance", engineInstance);
        binding.setProperty("manager", this);
        GroovyShell shell = new GroovyShell(binding);
        Script instructions = shell.parse(respawnInstructions);
        instructions.run();
    }
}
