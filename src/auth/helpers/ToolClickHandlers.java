package auth.helpers;

import auth.auth_ui_components.SimpleMessageDialog;
import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import gamedata.Resource;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static auth.Colors.DEFAULT_TEXT_COLOR;
import static auth.helpers.ScreenHelpers.*;
import static gamecenter.RunGameCenter.bebasKaiMedium;
import static gamedata.Resource.ResourceType.*;

/**
 * Author: Anshu Dwibhashi
 * All methods in this class will be of the name as defined in tools.json in the data folder. Since all methods in this class will be invoked
 * by reflection, it is critical to follow this convention.
 */
public class ToolClickHandlers {
    public static void handleNewScene (CanvasScreen context) {
        int newSceneCount = context.createNewScene();
        var sceneText = new Text("Scene " + newSceneCount);
        sceneText.setFont(bebasKaiMedium);
        sceneText.setFill(DEFAULT_TEXT_COLOR);
        context.getPagination().addPage(sceneText);
    }

    public static void handleDeleteCurrentSelection(CanvasScreen context) {
        var game = context.getGame();
        var currentScene = game.scenes.get(context.getCurrentScene());
        if (context.selectedType == null) {
            // Delete current scene
            deleteScene(game, context, currentScene);
        } else if (context.selectedType == Image.class) {
            // Delete img res
            deleteImgRes(game, context, currentScene);
            initialiseImagesGrid(context);
            initialiseObjectsGrid(context);
        } else if (context.selectedType == AudioClip.class) {
            // Delete audio res
            game.resources.removeIf(n -> n.resourceType.equals(AUDIO_RESOURCE) && n.resourceID.equals(context.selectedID));
            initialiseAudioGrid(context);
        } else if (context.selectedType == Color.class) {
            // Delete color res
            deleteColorResource(game, context, currentScene);
            initialiseColorGrid(context);
            initialiseObjectsGrid(context);
        } else if (context.selectedType == GameObject.class) {
            // Delete object
            deleteObject(game, context, currentScene);
            initialiseObjectsGrid(context);
        } else if (context.selectedType == Instance.class) {
            // Delete instance
            deleteInstance(game, context, currentScene);
        }

        clearSelection(context);
        repopulatePropertiesPane(context);
    }

    private static void deleteScene(Game game, CanvasScreen context, gamedata.Scene currentScene) {
        if (game.scenes.size() == 1) {
            // Don't delete scene bc it's the last one left.
            new SimpleMessageDialog("You can't delete your last remaining scene").show();
            return;
        }
        int current = game.scenes.indexOf(currentScene);
        game.scenes.remove(currentScene);
        context.getPagination().removePage(current);
        if (game.scenes.size() == current) {
            context.switchToScene(current - 1, true);
        } else {
            context.switchToScene(current, true);
        }
    }

    private static void deleteInstance(Game game, CanvasScreen context, gamedata.Scene currentScene) {
        for (var scene : game.scenes) {
            scene.instances.removeIf(n -> n.instanceID.equals(context.selectedID));
            if (scene == currentScene)
                refreshCanvas(context);
        }
    }

    private static void deleteObject(Game game, CanvasScreen context, gamedata.Scene currentScene) {
        game.gameObjects.removeIf(n -> n.objectID.equals(context.selectedID));
        for (var scene : game.scenes) {
            scene.instances.removeIf(n -> n.instanceOf.equals(context.selectedID));
            if (scene == currentScene)
                refreshCanvas(context);
        }
    }

    private static void deleteImgRes(Game game, CanvasScreen context, gamedata.Scene currentScene) {
        for (var scene : game.scenes) {
            if (scene.bgImage.equals(context.selectedID)) {
                scene.bgImage = "";
            }
            for (var instance : scene.instances) {
                if (instance.bgImage.equals(context.selectedID)) {
                    instance.bgImage = "";
                }
            }
            if (scene == currentScene)
                refreshCanvas(context);
        }
        for (var obj : game.gameObjects) {
            if (obj.bgImage.equals(context.selectedID)) {
                obj.bgImage = "";
            }
        }
        game.resources.removeIf(n -> n.resourceType.equals(IMAGE_RESOURCE) && n.resourceID.equals(context.selectedID));
    }

    private static void deleteColorResource(Game game, CanvasScreen context, gamedata.Scene currentScene) {
        for (var scene : game.scenes) {
            if (scene.bgColor.equals(context.selectedID)) {
                scene.bgColor = "";
            }
            for (var instance : scene.instances) {
                if (instance.bgColor.equals(context.selectedID)) {
                    instance.bgColor = "";
                }
            }
            if (scene == currentScene)
                refreshCanvas(context);
        }
        for (var obj : game.gameObjects) {
            if (obj.bgColor.equals(context.selectedID)) {
                obj.bgColor = "";
            }
        }
        game.resources.removeIf(n -> n.resourceType.equals(COLOR_RESOURCE) && n.resourceID.equals(context.selectedID));
    }

    public static void handleNewColor(CanvasScreen context) {
        final var stage = new Stage();

        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(e -> {
            stage.close();
            Color c = colorPicker.getValue();
            var r = new Resource();
            r.resourceType = Resource.ResourceType.COLOR_RESOURCE;
            r.resourceID = "color_"+(context.getResourcesCount(Resource.ResourceType.COLOR_RESOURCE)+1);
            r.src = c.toString();
            context.getGame().resources.add(r);
            initialiseColorGrid(context);
        });
        var scene = new Scene(colorPicker);
        stage.setScene(scene);
        stage.show();
        System.out.println("Added colour resource");
    }

    public static void handleNewAudioRes(CanvasScreen context) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Audio File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        var r = new Resource();
        r.resourceType = Resource.ResourceType.AUDIO_RESOURCE;
        r.resourceID = "audio_"+(context.getResourcesCount(Resource.ResourceType.AUDIO_RESOURCE)+1);
        r.src = file.getAbsolutePath();
        context.getGame().resources.add(r);
        initialiseAudioGrid(context);
    }

    public static void handleNewImgRes(CanvasScreen context) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        var r = new Resource();
        r.resourceType = IMAGE_RESOURCE;
        r.resourceID = "img_"+(context.getResourcesCount(IMAGE_RESOURCE)+1);
        r.src = file.getAbsolutePath();
        context.getGame().resources.add(r);
        initialiseImagesGrid(context);
    }

    public static void handleNewObject (CanvasScreen context) {
        var object = new GameObject();
        object.objectID = "object_"+(context.getGame().gameObjects.size()+1);
        context.getGame().gameObjects.add(object);
        initialiseObjectsGrid(context);
        System.out.println("Created new object");
        System.out.println("Currently selected: " + (context.currentlySelected == null));
    }

}
