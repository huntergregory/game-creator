package Engine.src.Manager;

import gamedata.Game;
import gamedata.GameObjects.Components.LivesComponent;
import gamedata.GameObjects.Instance;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class Die extends InstanceModifierEvent {
    private static final int NUM_ARGS = 0;

    public Die(Game game) {
        super(game, NUM_ARGS);
    }

    @Override
    protected void modifyInstance(Instance instance, Object... args) {
        var instances = myGame.currentScene.instances;
        if(instance.hasComponent(LivesComponent.class)){
            LivesComponent lives = instance.getComponent(LivesComponent.class);
            if (lives.expired())
                instances.remove(instance);
            else {
                respawn(instance, lives.getRespawnInstructions());
                lives.removeLife();
            }
        }
        else
            instances.remove(instance);
    }

    //FIXME - should be another event
    private void respawn(Instance instance, String respawnInstructions){
        Binding binding = new Binding();
        binding.setProperty("instance", instance);
        binding.setProperty("manager", this);
        GroovyShell shell = new GroovyShell(binding);
        Script instructions = shell.parse(respawnInstructions);
        instructions.run();
    }
}
