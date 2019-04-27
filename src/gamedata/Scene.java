package gamedata;

import gamedata.GameObjects.Instance;

import java.util.ArrayList;

public class Scene {
    public ArrayList<Instance> instances;
    public String sceneLogic, sceneID, bgColor, bgImage;

    public Scene() {
        instances = new ArrayList<>();
        sceneLogic = "";
        bgColor = "";
        sceneID = "";
        bgColor = "";
        bgImage = "";
    }
}
