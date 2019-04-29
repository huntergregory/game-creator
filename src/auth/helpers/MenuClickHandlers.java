package auth.helpers;

import auth.screens.CanvasScreen;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.GameObject;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import uiutils.panes.Pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Optional;
import java.util.Scanner;

import static auth.helpers.ScreenHelpers.initialiseGrids;
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

    public static void handleSaveToCloud (CanvasScreen context) {
        try {
            GameCloudWrapper gcw = context.getGameCloudWrapper();
            gcw.game = context.getGame();
            String SERVICE_ACCOUNT_JSON_PATH = "/Users/anshudwibhashi/work/school/CS308/voogasalad_crackingopen/lib/TMTP-b2dc645337e7.json";
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
        // TODO
        System.out.println("handleLoadFromCloud called");
    }

    public static void handleAddCollaborators (CanvasScreen context) {
        // TODO
        System.out.println("handleAddCollaborators called");
    }
}
