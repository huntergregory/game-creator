package gamedata;

import java.util.ArrayList;

public class Game {
    public ArrayList<Scene> scenes;
    public ArrayList<GameObject> gameObjects;
    public ArrayList<Resource> resources;

    public Game() {
        scenes = new ArrayList<>();
        gameObjects = new ArrayList<>();
        resources = new ArrayList<>();
    }
}
