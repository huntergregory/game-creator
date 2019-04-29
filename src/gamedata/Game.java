package gamedata;

import gamedata.GameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Scene> scenes;
    public List<GameObject> gameObjects;
    public List<Resource> resources;
    public int currentLevel;

    public Game() {
        scenes = new ArrayList<>();
        gameObjects = new ArrayList<>();
        resources = new ArrayList<>();
        currentLevel = 0;
    }
}
