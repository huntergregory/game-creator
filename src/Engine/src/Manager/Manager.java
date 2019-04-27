package Engine.src.Manager;

import NoEntityException;
import gamedata.Components.Component;
import gamedata.Components.LivesComponent;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Map;

public class Manager {
    private Map<Integer, Map<Class<? extends Component>, Component>> myEntityMap;

    public Manager(Map<Integer, Map<Class<? extends Component>, Component>> entityMap, double stepTime) {
        myEntityMap = entityMap;
    }

    public void call(String commandClass, String instanceID, Object ... args) {

    }


    public void die(int entityID) {
        //TODO error checking, does removing a non-existent Entity work
        if(hasComponent(entityID, LivesComponent.class)){
            LivesComponent lives = getComponent(entityID, LivesComponent.class);
            if (lives.expired()) myEntityMap.remove(entityID);
            else {
                respawn(entityID, lives.getRespawnInstructions());
                lives.removeLife();
            }
        }
        else myEntityMap.remove(entityID);
    }

    private void respawn(int entityID, String respawnInstructions){
        Binding binding = new Binding();
        binding.setProperty("ID", entityID);
        binding.setProperty("manager", this);
        GroovyShell shell = new GroovyShell(binding);
        Script instructions = shell.parse(respawnInstructions);
        instructions.run();
    }
}
