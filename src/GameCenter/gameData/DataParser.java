package GameCenter.gameData;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static GameCenter.utilities.Strings.*;

/**
 * Parses a json file and returns data as a list of DataStruct objects.
 *
 * @author Januario Carreiro and Anshu Dwibhashi
 */
public class DataParser {
    /**
     * Parses a json file and returns data as a list of DataStruct objects.
     *
     * @param path path of json file
     * @return List of DataStruct objects
     * @throws FileNotFoundException if .json file does not exist at specified path
     */
    public static List<DataStruct> parseConfig(String path) throws FileNotFoundException {
        var gamesList = new ArrayList<DataStruct>();
        JSONObject obj = new JSONObject(new Scanner(new File(path)).useDelimiter("\\Z").next());
        var games = obj.getJSONArray("games");
        for(Object o : games) {
            var game = (JSONObject) o;
            var gameStruct = new DataStruct(
                    game.getString("name"),
                    game.getString("img"),
                    game.getString("desc"),
                    game.getString("game_source"),
                    game.getString("rating"),
                    game.getString("favorite"));
            gamesList.add(gameStruct);
        }
        return gamesList;
    }
}
