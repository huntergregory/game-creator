package auth.screens;

import auth.RunAuth;
import auth.UIElement;
import auth.auth_ui_components.InstanceUI;
import auth.auth_ui_components.Selectable;
import auth.helpers.DataHelpers;
import auth.pagination.PaginationUIElement;
import gamedata.Game;
import gamedata.Instance;
import gamedata.Resource;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static auth.Colors.*;
import static auth.Dimensions.*;
import static auth.Strings.*;
import static auth.helpers.DataHelpers.createNewScene;
import static auth.helpers.ScreenHelpers.*;

public class CanvasScreen extends Screen {
    private RunAuth context;
    private Group container;
    private Stage stage;
    private Game game;

    public Selectable currentlySelected = null;
    public Class selectedType = null;
    public String selectedID = "";

    public VBox getObjectGrid() {
        return objectGrid;
    }

    private VBox objectGrid;

    public VBox getImageGrid() {
        return imageGrid;
    }

    private VBox imageGrid;

    public VBox getAudioGrid() {
        return audioGrid;
    }

    private VBox audioGrid;

    public VBox getColorGrid() {
        return colorGrid;
    }

    private VBox colorGrid;

    private int currentScene = 0;

    public PaginationUIElement getPagination() {
        return pagination;
    }

    public void setPagination(PaginationUIElement pagination) {
        this.pagination = pagination;
    }

    private PaginationUIElement pagination;

    private List<UIElement> possessedElements;

    public CanvasScreen() {
        possessedElements = new ArrayList<>();
        game = new Game();
        game.scenes.add(DataHelpers.createNewScene(game.scenes.size()+1));
        objectGrid = new VBox(5);
        imageGrid = new VBox(5);
        audioGrid = new VBox(5);
        colorGrid = new VBox(5);
    }

    public int createNewScene() {
        game.scenes.add(DataHelpers.createNewScene(game.scenes.size()+1));
        return game.scenes.size();
    }

    public Game getGame() {
        return game;
    }

    public int getResourcesCount(Resource.ResourceType type) {
        int count = 0;
        for (Resource r : game.resources) {
            if (r.resourceType == type)
                count ++;
        }
        return count;
    }

    public void switchToScene(int index, boolean deselect) {
        currentScene = index;
        if (deselect) {
            selectedType = null;
            selectedID = null;
            currentlySelected = null; // deselect everything so scene has focus
        }
        // TODO: loadScene(index);
        System.out.println("Current scene is "+currentScene+" and it has "+game.scenes.get(currentScene).instances.size()+" instances.");
        refreshCanvas(this);
        repopulatePropertiesPane(this);
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void registerNewUIElement(UIElement... elements) {
        for (UIElement element : elements) {
            this.possessedElements.add(element);
            this.container.getChildren().add(element.getView());
        }
    }

    public UIElement getUIElementById(String id) {
        for (UIElement element : possessedElements) {
            if (element.getID().equals(id)) return element;
        }
        return null;
    }

    public void removeUIElement(UIElement... elements) {
        for (UIElement element : elements) {
            this.container.getChildren().remove(element.getView());
            this.possessedElements.remove(element);
        }
    }

    /**
     * Method to create create a new stage
     * @param stage Parent stage
     * @param context Reference to the parent object
     * @return a stage representing the main stage
     */
    public Stage createScreen(Stage stage, RunAuth context) {
        var root = new Group();
        container = new Group();
        this.stage = stage;
        root.getChildren().add(container);

        var scene = new Scene(root, ENV_WINDOW_WIDTH, ENV_WINDOW_HEIGHT, BG_COLOR);
        initScene(this, scene, root);

        this.context = context;
        stage.setScene(scene);
        stage.setTitle(DEFAULT_TITLE);
        initialiseGrids(this);
        return stage;
    }
}
