package gamedata;

import gamedata.GameObjects.Components.EnvironmentComponent;
import gamedata.GameObjects.Instance;

import java.util.HashSet;
import java.util.Set;

public class Scene {
    public Set<Instance> instances;
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

    public Scene(Set<Instance> instSet, String scnLogic, String scnID){
        instances = instSet;
        sceneLogic = scnLogic;
        sceneID = scnID;
        bgColor = "";
        sceneID = "";
        bgColor = "";
        bgImage = "";
        defaultEnvironment = new EnvironmentComponent(0, 5, 0, 0); //FIXME defaults
    }
}
