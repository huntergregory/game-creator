package auth.helpers;

import auth.screens.CanvasScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.GameObject;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uiutils.panes.Pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static auth.helpers.ScreenHelpers.initialiseGrids;

public class MenuClickHandlers {
    public static uiutils.panes.Pane paneA;
    public static Pane paneB;
    public static VBox paneContainer;
    public static Pane namePane;

    public static void handleSaveToFile (CanvasScreen context) throws Exception {
        Game o = context.getGame();
        String contents = new Gson().toJson(o, new TypeToken<Game>(){}.getType());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game File");
        var stage = new Stage(); stage.setTitle("Save Game File");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".game")) {
                String newPath = file.getAbsolutePath()+".game";
                file = new File(newPath);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(contents);
            writer.close();
        }
        context.changeTitle(file.getName());
        ScreenHelpers.closeMenu(context, paneA, paneB, paneContainer, namePane);
    }

    public static void handleLoadFromFile (CanvasScreen context) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Game File");
        var stage = new Stage(); stage.setTitle("Open Game File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".game")) {
                // TODO: Tell user it's not a good file
                return;
            }
            context.changeTitle(file.getName());
            try {
                String contents = new Scanner(file).useDelimiter("\\Z").next();
                Game game = new Gson().fromJson(contents, new TypeToken<Game>() {}.getType());
                ScreenHelpers.closeMenu(context, paneA, paneB, paneContainer, namePane);
                context.setGame(game);
            } catch (Exception e) {
                // TODO: Tell user it's not a good file
            }
        }
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
