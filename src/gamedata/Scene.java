package gamedata;

import java.util.ArrayList;

public class Scene {
    public ArrayList<Instance> instances;
    public String sceneLogic, sceneID, bgColor, bgImage;

    public Scene() {
        instances = new ArrayList<>();
        bgColor = "";
        sceneLogic = "";
        sceneID = "";
        bgColor = "";
        bgImage = "";
    }
}
