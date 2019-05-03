package dummy_player;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayerHelpers {
    public static Game getGameFromFile(String path) throws FileNotFoundException {
        String contents = new Scanner(new File(path)).useDelimiter("\\Z").next();
        return new Gson().fromJson(contents, new TypeToken<Game>(){}.getType());
    }
}
