package Engine.src.Controller;

import gamedata.Game;

import java.util.List;

public class GameController {

    private final double myStepTime;
    private final double myScreenWidth;
    private final double myScreenHeight;
    private List<String> myGameScript;
    private Game myGame;


    public GameController(double stepTime, double screenWidth, double screenHeight, Game game) {
        myStepTime = stepTime;
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        myGame = game;
    }

    public LevelController getLevelController(){
        return new LevelController(myStepTime, myScreenWidth, myScreenHeight, myGame);
    }




}
