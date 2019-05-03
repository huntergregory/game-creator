package gamedata;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Scene> scenes;
    public List<GameObject> gameObjects;
    public List<Resource> resources;
    public int currentLevel;
    public boolean scrollsHorizontally;
    public boolean scrollsVertically;

    public Game() {
        scenes = new ArrayList<>();
        gameObjects = new ArrayList<>();
        resources = new ArrayList<>();
        currentLevel = 0;
        scrollsHorizontally = true;
        scrollsVertically = false;
    }
}
