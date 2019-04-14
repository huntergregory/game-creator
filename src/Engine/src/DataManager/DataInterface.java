
package Engine.src.DataManager;

public interface DataInterface {
    void saveGame(String fileName, Engine.src.DataManager.GameInformation gameInformation);

    Engine.src.DataManager.GameInformation loadGame(String fileName);
}
