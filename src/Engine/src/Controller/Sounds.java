package Engine.src.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to hold the strings of sounds that need to be played and the condition of whether or not to loop them
 */
public class Sounds {
    private Map<String, Boolean> mySounds;

    /**
     * The map which holds the strings of sounds and the loop condition
     */
    public Sounds() {
        mySounds = new HashMap<>();
    }

    /**
     * Called by the author when a sound should get played in the game at a specific time in a groovy script
     * @param soundFile String sent by the auth of the sound file that needs to be played
     * @param looping Looping condition - true = loop
     */
    public void addToSounds(String soundFile, boolean looping) {
        mySounds.put(soundFile, looping);
    }

    /**
     * Give the map of strings of sounds and the loop condition
     * @return map of strings of sounds and the loop condition
     */
    public Map<String, Boolean> getSounds() {
        return mySounds;
    }

    /**
     * Clear the map of strings of sounds and the loop condition
     */
    public void clearSounds() {
        mySounds.clear();
    }
}
