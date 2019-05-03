package gamedata.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Serializer {
    public static final String FILE_PATH = "testgame.txt";

    private Gson myGson;

    public Serializer() {
        myGson = new Gson();
    }

    public void serialize(List<String> scenes) throws IOException {
        String jsonString = myGson.toJson(scenes);
        saveJSON(jsonString);
    }

    public List<String> deserialize(String filePath) throws IOException {
        String jsonString = Files.readString(Paths.get(filePath));
        var listOfMyClassObject = new TypeToken<List<String>>() {}.getType();

        return myGson.fromJson(jsonString, listOfMyClassObject);
    }

    private void saveJSON(String jsonString) throws IOException {
        File file = new File(FILE_PATH);
        file.createNewFile();
        PrintWriter out = new PrintWriter(file);
        out.println(jsonString);
        out.close();
    }
}
