package auth.helpers;

import auth.RunTest;
import auth.auth_fxml_controllers.CollisionController;
import auth.auth_fxml_controllers.ObjectScriptController;
import auth.screens.CanvasScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uiutils.panes.Pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void handleAddObjectScript(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/objectScript.fxml");
            ObjectScriptController controller = loader.getController();
            Game o = context.getGame();
            Scene scene = o.scenes.get(0);
            scene.sceneLogic = scene.sceneLogic + controller.myScript;
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }

    public static void handleAddCollision(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/collision.fxml");
            CollisionController controller = loader.getController();
            Game o = context.getGame();
            Scene scene = o.scenes.get(0);
            scene.sceneLogic = scene.sceneLogic + controller.myScript;
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
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

    private static FXMLLoader addPopup(String fxmlString) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RunTest.class.getResource(fxmlString));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new javafx.scene.Scene(root));
        stage.setResizable(false);
        stage.showAndWait();

        return loader;
    }
}
