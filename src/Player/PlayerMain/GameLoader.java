package Player.PlayerMain;

import java.util.List;

/**
 * @deprecated
 */
public class GameLoader {

    private int myLevelNumber;
    private List<String> myGameLogic;

    public GameLoader(int levelNumber, List<String> gameLogic){
        myGameLogic = gameLogic;
        myLevelNumber = levelNumber;
    }

    public int getMyLevelNumber() {
        return myLevelNumber;
    }

    public String getLevelLogic(){
        return myGameLogic.get(myLevelNumber);
    }

    public List<String> getGameLogic(){
        return myGameLogic;
    }
}
