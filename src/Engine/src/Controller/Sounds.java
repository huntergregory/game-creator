package Engine.src.Controller;

import java.util.HashMap;
import java.util.Map;

public class Sounds {
    private Map<String, Boolean> mySounds;

    public Sounds() {
        mySounds = new HashMap<>();
    }

    public void addToSounds(String soundFile, boolean looping) {
        mySounds.put(soundFile, looping);
    }

    public Map<String, Boolean> getSounds() {
        return mySounds;
    }

    public void clearSounds() {
        mySounds.clear();
    }
}