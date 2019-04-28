package auth.helpers;

import auth.screens.CanvasScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.GameObject;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static auth.Colors.DEFAULT_TEXT_COLOR;
import static gamecenter.RunGameCenter.bebasKaiMedium;

public class MenuClickHandlers {
    public static void handleSaveToFile (CanvasScreen context) throws Exception {
        Game o = context.getGame();
        String contents = new Gson().toJson(o, new TypeToken<Game>(){}.getType());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game File");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            if (!file.getName().endsWith(".game")) {
                String newPath = file.getAbsolutePath()+".game";
                file = new File(newPath);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(contents);
            writer.close();
        }
        // TODO: Change window title
    }

    public static void handleLoadFromFile (CanvasScreen context) {
        // TODO
        System.out.println("handleLoadFromFile called");
    }

    public static void handleSaveToCloud (CanvasScreen context) {
        // TODO
        System.out.println("handleSaveToCloud called");
    }

    public static void handleLoadFromCloud (CanvasScreen context) {
        // TODO
        System.out.println("handleLoadFromCloud called");
    }

    public static void handleAddCollaborators (CanvasScreen context) {
        // TODO
        System.out.println("handleAddCollaborators called");
    }
}
