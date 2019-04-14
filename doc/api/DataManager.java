package Player;

public interface DataManager {
    /**
     * Saves the initial state of all of the gameObjects in the game, as well as the rules and any special circumstances
     * that apply to the game to a .json file
     */
    void saveGame(GameInformation gameInformation);

    /**
     * Loads game information object, that will contain all of the necessary information to run the game or to edit it
     * in the authoring environment
     */
    GameInformation loadGame(String filename);
}
