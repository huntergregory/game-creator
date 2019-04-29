package Engine.src.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return new HashMap<>(mySounds);
    }

    public void clearSounds() {
        mySounds.clear();
    }
}
