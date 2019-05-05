package GameCenter.gameData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * DataWriter can write to the player_data.json file and update it. First, it loads the information that is already in
 * the .json file and updates it with the new information based on what parameters and methods are called.
 *
 * For example, if the rating of game is to be changed, the new rating and gameIndex should be passed to writeRating(),
 * which will then use the writeToFile() and writeValue() methods to update that specific game's rating to the new value.
 */
public class DataWriter {
    private Gson myGson;
    private final String filePath = "data/player_data.json";
    private final String JsonObjectName = "games";
    private File jsonFile = new File(filePath);

    /**
     * Initializes GsonBuilder() with pretty printing so that output is easily readable by humans.
     */
    public DataWriter(){
        try {
            myGson = new GsonBuilder().setPrettyPrinting().create();
        } catch (Exception e) {
            // should not occur
        }
    }

    private void writeToFile(JSONObject jo) {
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(jo.toString());
        String gameString = myGson.toJson(je);

        if(jsonFile.exists()){
            jsonFile.delete();
            try {
                jsonFile.createNewFile();
                FileWriter writer = new FileWriter(filePath);
                writer.write(gameString);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject writeValue(String key, String value, int gameIndex) throws FileNotFoundException {
        var jo = new JSONObject(new Scanner(new File(filePath)).useDelimiter("\\Z").next());

        var games = jo.getJSONArray(JsonObjectName);
        int counter = 0;
        for(Object o : games) {
            var game = (JSONObject) o;
            if (counter == gameIndex) {
                game.put(key, value);
            }
            counter++;
        }

        return jo;
    }

    /**
     * Updates game's rating with new value given by rating parameter.
     *
     * @param rating double as a string
     * @param gameIndex unique game index
     * @throws FileNotFoundException if player_data.json is not where it should be
     */
    public void writeRating (String rating, int gameIndex) throws FileNotFoundException {
        writeToFile(writeValue("rating", rating, gameIndex));
    }

    /**
     * Update's game's favorite field with string representing a true or false boolean
     *
     * @param favorite boolean as a string
     * @param gameIndex unique game index
     * @throws FileNotFoundException if player_data.json is not where it should be
     */
    public void writeFavorite (String favorite, int gameIndex) throws FileNotFoundException {
        writeToFile(writeValue("favorite", favorite, gameIndex));
    }
}
