package auth.screens;

import auth.RunAuth;
import auth.UIElement;
import auth.auth_ui_components.InstanceUI;
import auth.auth_ui_components.Selectable;
import auth.helpers.DataHelpers;
import auth.helpers.GameCloudWrapper;
import auth.pagination.PaginationUIElement;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.rest.Pusher;
import gamedata.Game;
import gamedata.Instance;
import gamedata.Resource;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uiutils.panes.BottomPane;

import java.util.ArrayList;
import java.util.Collections;
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
    private Pusher pusherSender = new Pusher("245192", "99244f3550e4ac4a1ce7", "934e0900e15a258cdd56");
    PusherOptions options = new PusherOptions().setCluster("mt1");
    private com.pusher.client.Pusher pusherReceiver = new com.pusher.client.Pusher("99244f3550e4ac4a1ce7", options);

    public Selectable currentlySelected = null;
    public Class selectedType = null;
    public String selectedID = "";

    private GameCloudWrapper gameCloudWrapper = new GameCloudWrapper();

    public GameCloudWrapper getGameCloudWrapper () {
        return gameCloudWrapper;
    }

    public void setGameCloudWrapper(GameCloudWrapper gcw) {
        this.gameCloudWrapper = gcw;
    }

    private boolean selfTrigger = false;
    public void triggerEvent(String gameID) {
        pusherSender.trigger("mainChannel", "update", gameID);
        selfTrigger = true;
    }

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
        gameCloudWrapper.owner = getLoggedInUsername();
        gameCloudWrapper.game = game;
        pusherSender.setCluster("mt1");

        pusherReceiver.connect();
        // Subscribe to a channel
        Channel channel = pusherReceiver.subscribe("mainChannel");
        channel.bind("update", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                if (!selfTrigger) {
                    System.out.println("Received request to update " + data);
                    selfTrigger = true;
                }
            }
        });
    }

    public int createNewScene() {
        game.scenes.add(DataHelpers.createNewScene(game.scenes.size()+1));
        return game.scenes.size();
    }

    public void changeTitle(String title) {
        stage.setTitle(title);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        switchToScene(0, true);
        initialiseGrids(this);
    }

    public int getResourcesCount(Resource.ResourceType type) {
        int count = 0;
        for (Resource r : game.resources) {
            if (r.resourceType == type)
                count ++;
        }
        return count;
    }

    public String getLoggedInUsername() {
        // TODO: Softcode this
        return "anshudwibhashi";
    }

    public String getLoggedInName() {
        return "Anshu";
    }

    public void switchToScene(int index, boolean deselect) {
        currentScene = index;
        if (deselect) {
            selectedType = null;
            selectedID = null;
            currentlySelected = null; // deselect everything so scene has focus
        }
        System.out.println("Current scene is "+currentScene+" and it has "+game.scenes.get(currentScene).instances.size()+" instances.");
        refreshCanvas(this);
        repopulatePropertiesPane(this);
        populateConsolePane(this, (BottomPane)getUIElementById(CONSOLE_PANE_ID));
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
