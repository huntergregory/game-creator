package ez_engine;

import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static ez_engine.RenderingHelpers.renderInstance;
import static ez_engine.RenderingHelpers.renderScene;

public class Engine {
    private Stage mainStage;
    private Game game;
    private int currentScene = 0;
    public KeyCode keyBeingPressed = null;

    public int getCurrentScene() {
        return currentScene;
    }

    public Engine (Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setGame(Game game) {
        this.game = game;
        RenderingHelpers.initGame(game);
    }

    public void loadScene(int sceneIndex, InstanceView ...instances) {
        if (game == null) throw new RuntimeException("Need to setGame() before using engine") ;
        for (var i : instances) {
            game.scenes.get(sceneIndex).instances.add(i.getInstance());
        }
        mainStage.setScene(renderScene(game.scenes.get(sceneIndex)));
        currentScene = sceneIndex;
        mainStage.getScene().setOnKeyPressed(e -> {
            Engine.this.keyBeingPressed = e.getCode();
        });

        mainStage.getScene().setOnKeyReleased(e -> {
            Engine.this.keyBeingPressed = null;
        });
    }

    public void loadSceneById(String id, InstanceView ...instances) {
        int index = 0;
        for (var scene : game.scenes) {
            if (scene.sceneID.equals(id)) {
                loadScene(index, instances);
                return;
            }
            index++;
        }
    }

    public InstanceView getInstanceById(String id) {
        return RenderingHelpers.getInstanceById(id);
    }

    public InstanceView createInstanceOf(String instanceOfId, double x, double y) {
        var newInstance = new Instance();
        GameObject instanceOf = null;
        for (var o : game.gameObjects) {
            if (o.objectID.equals(instanceOfId)) {
                instanceOf = o;
                break;
            }
        }
        newInstance.bgImage = instanceOf.bgImage; newInstance.bgColor = instanceOf.bgColor; newInstance.instanceOf = instanceOf.objectID;
        newInstance.instanceID = "instance_"+(game.scenes.get(currentScene).instances.size()+1);
        newInstance.instanceLogic = "// Type your Groovy scripts for " + newInstance.instanceID + " here";
        newInstance.zIndex = 1;
        newInstance.width = (instanceOf.width > 0 ? instanceOf.width : 60);
        newInstance.height = (instanceOf.height > 0 ? instanceOf.height : 60);
        newInstance.x = x;
        newInstance.y = y;

        var pane = renderInstance(newInstance);
        ((Pane) mainStage.getScene().getRoot()).getChildren().add(pane);
        return new InstanceView(newInstance, pane);
    }
}
