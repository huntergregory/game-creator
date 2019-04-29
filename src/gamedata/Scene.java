package gamedata;

import java.util.HashSet;
import java.util.Set;

public class Scene {
    public Set<Instance> instances;
    public String sceneLogic, sceneID, bgColor, bgImage;

    public Scene() {
        instances = new HashSet<>();
        sceneLogic = "";
        bgColor = "";
        sceneID = "";
        bgImage = "";
    }
}
