package auth.helpers;

import auth.screens.CanvasScreen;
import gamedata.GameObject;
import gamedata.Resource;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static auth.Colors.DEFAULT_TEXT_COLOR;
import static auth.helpers.ScreenHelpers.initialiseGrids;
import static auth.RunAuth.bebasKaiMedium;

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
            initialiseGrids(context);
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
        initialiseGrids(context);
    }

    public static void handleNewImgRes(CanvasScreen context) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        var r = new Resource();
        r.resourceType = Resource.ResourceType.IMAGE_RESOURCE;
        r.resourceID = "img_"+(context.getResourcesCount(Resource.ResourceType.IMAGE_RESOURCE)+1);
        r.src = file.getAbsolutePath();
        context.getGame().resources.add(r);
        initialiseGrids(context);
    }

    public static void handleNewObject (CanvasScreen context) {
        var object = new GameObject();
        object.objectID = "object_"+(context.getGame().gameObjects.size()+1);
        context.getGame().gameObjects.add(object);
        initialiseGrids(context);
        System.out.println("Created new object");
    }

}
