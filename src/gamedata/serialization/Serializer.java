package gamedata.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Serializer {
    private static final String FILE_PATH = ""; //FIXME

    private Gson myGson;

    public Serializer() {
        myGson = new Gson();
    }

    public void serialize(List<String> scenes) {
        String jsonString = myGson.toJson(scenes);
        saveJSON(jsonString);
    }

    public List<String> deserialize(String filePath) throws IOException {
        String jsonString = Files.readString(Paths.get(filePath));
        var listOfMyClassObject = new TypeToken<List<String>>() {}.getType();

        return myGson.fromJson(jsonString, listOfMyClassObject);
    }

    private void saveJSON(String jsonString) {
        //TODO
    }
}
