package usecases;

import java.util.ArrayList;

/**
 * Contains the code for example 1 and 2.
 */
public class ExampleCode{

    //Place a block at the bottom of the screen, with an enemy on it, and publish it as a game
    public void exampleOne(){
        var myBlocks = new ArrayList<Block>();
        var myEnemies = new ArrayList<Enemy>();

        myBlocks.add(new Block(xLoc, yLoc, resistance, blockImage));
        myEnemies.add(new Enemy(xLoc, yLoc + blockImage.getWidth()/2, hitpoints, behavior))

        GameInformation testGame = new GameInformation(myBlocks, myEnemies);

        DataManager.saveGame(testGame);
    }

    //Load the above example back into the authoring environment to add another block before publishing
    public void exampleTwo(){
        myBlocks = new ArrayList<Block>();
        myEnemies = new ArrayList<Enemy>();

        myBlocks = DataManager.loadGame(fileName).getBlocks();
        myEnemies = DataManager.loadGame(fileName).getEnemies();

        myBlocks.add(new Block(xLoc2, yLoc2, resistance, blockImage));
        DataManager.saveGame(myBlocks, myEnemies)
    }

}