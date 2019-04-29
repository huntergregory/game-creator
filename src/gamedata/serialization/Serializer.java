package gamedata.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class Serializer {
    private static final String FILE_PATH = ""; //FIXME

    Gson myGson;

    public Serializer() {
        myGson = new Gson();
    }

    public void serialize(List<String> scenes) {
        String jsonString = myGson.toJson(scenes);
        saveJSON(jsonString);
    }

    public List<String> deserialize(String filePath) {
        try {
            String jsonString = Files.readString(Paths.get(filePath));
            var listOfMyClassObject = new TypeToken<List<String>>() {}.getType();

            Gson gson = new Gson();
            return gson.fromJson(jsonString, listOfMyClassObject);
        }
        catch (IOException e) {
            System.out.println("File error");
        }

    }

    private void saveJSON(String jsonString) {
        //TODO
    }
}
