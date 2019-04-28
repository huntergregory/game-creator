package gamedata;

import gamedata.GameObjects.Instance;

import java.util.HashSet;

public class Scene {
    public HashSet<Instance> instances;
    public String sceneLogic, sceneID, bgColor, bgImage;

    public Scene() {
        instances = new HashSet<>();
        sceneLogic = "";
        bgColor = "";
        sceneID = "";
        bgColor = "";
        bgImage = "";
    }
}
