package gamedata;

import gamedata.GameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Scene> scenes;
    public Scene currentScene;
    public List<GameObject> gameObjects;
    public List<Resource> resources;

    public Game() {
        scenes = new ArrayList<>();
        gameObjects = new ArrayList<>();
        resources = new ArrayList<>();
    }

    public void addScene(Scene scene){
        scenes.add(scene);
    }

    public void setCurrentScene(Scene scene) {
        currentScene = scene;
    }
}
