package auth.helpers;

import auth.RunTest;
import auth.auth_fxml_controllers.CollisionController;
import auth.auth_fxml_controllers.KeyEventController;
import auth.auth_fxml_controllers.ObjectScriptController;
import auth.auth_fxml_controllers.SceneScriptController;
import auth.screens.CanvasScreen;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import uiutils.panes.BottomPane;
import uiutils.panes.Pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static auth.Strings.CONSOLE_PANE_ID;
import java.io.*;
import java.util.Scanner;

import static auth.Strings.CONSOLE_PANE_ID;
import static auth.helpers.DataHelpers.SERVICE_ACCOUNT_JSON_PATH;
import static java.nio.charset.StandardCharsets.UTF_8;

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
                e.printStackTrace();
                // TODO: Tell user it's not a good file
            }
        }
    }

    public static void handleAddObjectScript(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/objectScript.fxml");
            ObjectScriptController controller = loader.getController();
            Game o = context.getGame();
            List<GameObject> gameObjects = o.gameObjects;
            for(GameObject go:gameObjects){
                if(go.objectID.equals(controller.objID)){
                    go.objectLogic = go.objectLogic + "\n" + controller.script;
                    System.out.print(go.objectID + " " + go.objectLogic);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }

    public static void handleAddCollision(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/collision.fxml");
            CollisionController controller = loader.getController();
            Game o = context.getGame();
            Scene scene = o.scenes.get(context.getCurrentScene());
            scene.sceneLogic = scene.sceneLogic + "\n" + controller.script;
            ScreenHelpers.populateConsolePane(context, (BottomPane)context.getUIElementById(CONSOLE_PANE_ID));
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }


    public static void handleAddKeyEvent(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/keyEvent.fxml");
            KeyEventController controller = loader.getController();
            Game o = context.getGame();
            Scene scene = o.scenes.get(context.getCurrentScene());
            scene.sceneLogic = scene.sceneLogic + "\n" +controller.script;
            ScreenHelpers.populateConsolePane(context, (BottomPane)context.getUIElementById(CONSOLE_PANE_ID));
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }

    public static void handleAddSceneScript(CanvasScreen context){
        try{
            FXMLLoader loader = addPopup("/auth_components_fxml/sceneScript.fxml");
            SceneScriptController controller = loader.getController();
            Game o = context.getGame();
            Scene scene = o.scenes.get(context.getCurrentScene());
            scene.sceneLogic = scene.sceneLogic + "\n" + controller.script;
            ScreenHelpers.populateConsolePane(context, (BottomPane)context.getUIElementById(CONSOLE_PANE_ID));
        } catch (IOException e) {
            System.out.println("Error loading the components fxml");
        }
    }

    public static void handleSaveToCloud (CanvasScreen context) {
        try {
            GameCloudWrapper gcw = context.getGameCloudWrapper();
            gcw.game = context.getGame();
            // Instantiates a client
            Storage storage =
                    StorageOptions.newBuilder()
                            .setCredentials(
                                    ServiceAccountCredentials.fromStream(
                                            new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                            .setProjectId("tmtp-spec")
                            .build()
                            .getService();

            if (gcw.gameID.isEmpty()) {
                TextInputDialog dialog = new TextInputDialog("examplegameid");
                dialog.setTitle("Choose gameID");
                dialog.setHeaderText("Must be alphanumeric only.");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                    if(StringUtils.isAlphanumeric(name)) {
                        gcw.gameID = name;
                        String contents = new Gson().toJson(gcw, new TypeToken<GameCloudWrapper>(){}.getType());
                        BlobId blobId = BlobId.of("voogasalad-files", name);
                        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/json").build();
                        Blob blob = storage.create(blobInfo, contents.getBytes(UTF_8));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invalid ID");
                        alert.setContentText("Needs to be alphanumeric");
                        alert.show();
                    }
                });
            } else {
                String contents = new Gson().toJson(gcw, new TypeToken<GameCloudWrapper>(){}.getType());
                BlobId blobId = BlobId.of("voogasalad-files", gcw.gameID);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/json").build();
                Blob blob = storage.create(blobInfo, contents.getBytes(UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleLoadFromCloud (CanvasScreen context) {
        try {
            // Instantiates a client
            Storage storage =
                    StorageOptions.newBuilder()
                            .setCredentials(
                                    ServiceAccountCredentials.fromStream(
                                            new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                            .setProjectId("tmtp-spec")
                            .build()
                            .getService();
            TextInputDialog dialog = new TextInputDialog("examplegameid");
            dialog.setTitle("Enter gameID");
            dialog.setHeaderText("Must be alphanumeric only.");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                if (StringUtils.isAlphanumeric(name)) {
                    Blob blob = storage.get(BlobId.of("voogasalad-files", name));
                    if (blob == null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Can't find file");
                        alert.setContentText("Check your gameID");
                        alert.show();
                    } else {
                        String contents = new String(blob.getContent());
                        GameCloudWrapper gcw = (new Gson().fromJson(contents, new TypeToken<GameCloudWrapper>() {
                        }.getType()));
                        if (gcw.owner.equals(context.getLoggedInUsername()) || gcw.allowAccess.contains(context.getLoggedInUsername())) {
                            context.changeTitle(name);
                            try {
                                Game game = gcw.game;
                                context.setGameCloudWrapper(gcw);
                                ScreenHelpers.closeMenu(context, paneA, paneB, paneContainer, namePane);
                                context.setGame(game);
                            } catch (Exception e) {
                                e.printStackTrace();
                                // TODO: Tell user it's not a good file
                            }
                        } else {
                            System.out.println("No access");
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("Needs to be alphanumeric");
                    alert.show();
                }
            });
        } catch ( Exception e) {e.printStackTrace();}
    }

    public static void handleChangeCollaborators (CanvasScreen context) {
        TextInputDialog dialog = new TextInputDialog(String.join(",", context.getGameCloudWrapper().allowAccess.toArray(new String[context.getGameCloudWrapper().allowAccess.size()])));
        dialog.setTitle("Choose who to collaborate with");
        dialog.setHeaderText("Enter usernames separated by commas");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            String allowed[] = name.split(",");
            context.getGameCloudWrapper().allowAccess.clear();
            context.getGameCloudWrapper().allowAccess.addAll(Arrays.asList(allowed));
            DataHelpers.sendNewCloudData(context);
        });
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
