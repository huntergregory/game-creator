package gamedata;

import java.util.ArrayList;

public class Scene {
    public ArrayList<Instance> instances;
    public String sceneLogic, sceneID, bgColor, bgImg;

    public Scene() {
        instances = new ArrayList<>();
    }
}
