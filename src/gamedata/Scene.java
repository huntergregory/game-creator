package gamedata;

import gamedata.GameObjects.Components.EnvironmentComponent;
import gamedata.GameObjects.Instance;

import java.util.HashSet;

public class Scene {
    public HashSet<Instance> instances;
    public EnvironmentComponent defaultEnvironment;
    public String sceneLogic, sceneID, bgColor, bgImage;

    public Scene() {
        instances = new HashSet<>();
        sceneLogic = "";
        bgColor = "";
        sceneID = "";
        bgColor = "";
        bgImage = "";
        defaultEnvironment = new EnvironmentComponent(0, 5, 0, 0); //FIXME defaults
    }
}
