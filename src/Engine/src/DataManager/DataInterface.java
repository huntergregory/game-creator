
package Engine.src.DataManager;

@Deprecated
public interface DataInterface {
    void saveGame(String fileName, Engine.src.DataManager.GameInformation gameInformation);

    Engine.src.DataManager.GameInformation loadGame(String fileName);
}
