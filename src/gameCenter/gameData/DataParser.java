package gameCenter.gameData;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataParser {
    public static List<DataStruct> parseConfig (String path) throws FileNotFoundException {
        var gamesList = new ArrayList<DataStruct>();
        JSONObject obj = new JSONObject(new Scanner(new File(path)).useDelimiter("\\Z").next());
        var games = obj.getJSONArray("games");
        for(Object o : games) {
            var game = (JSONObject) o;
            var gameStruct = new DataStruct(game.getString("name"),
                    game.getString("img"),
                    game.getString("desc"),
                    game.getString("game_source"));
            gamesList.add(gameStruct);
        }
        return gamesList;
    }
}
