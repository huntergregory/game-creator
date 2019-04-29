package Engine.src.Controller;

import gamedata.Game;

import java.util.List;

public class GameController {

    private final double myStepTime;
    private final double myScreenWidth;
    private final double myScreenHeight;
    private double myLevelWidth;
    private double myLevelHeight;
    private List<String> myGameScript;
    private Game myGame;


    public GameController(double stepTime, double screenWidth, double screenHeight, double levelWidth, double levelHeight,
                      List<String> gameScript) {

        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        myLevelWidth = levelWidth;
        myLevelHeight = levelHeight;
        myGameScript = gameScript;
        myGame = new Game();
    }

    public LevelController getLevelController(int levelIndex){
        return new LevelController(myStepTime, myScreenWidth, myScreenHeight, myLevelWidth, myLevelHeight, myGame, myGameScript.get(levelIndex), levelIndex);
    }




}
