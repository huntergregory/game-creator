package GameCenter.gameData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataWriter {
    private Gson myGson;
    private final String filePath = "data/player_data.json";
    private final String JsonObjectName = "games";
    private File jsonFile = new File(filePath);

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

    public void writeRating (String rating, int gameIndex) throws FileNotFoundException {
        writeToFile(writeValue("rating", rating, gameIndex));
    }

    public void writeFavorite (String favorite, int gameIndex) throws FileNotFoundException {
        writeToFile(writeValue("favorite", favorite, gameIndex));
    }
}
