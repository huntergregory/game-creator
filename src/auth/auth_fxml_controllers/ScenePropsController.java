package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import static auth.helpers.DataHelpers.*;
import static auth.helpers.ScreenHelpers.*;

public class ScenePropsController extends JXMLController{
    private Game game;
    private int currentScene;

    @FXML public TextField sceneIDField, bgImgField, bgColorField;

    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        currentScene = context.getCurrentScene();

        populateFormUsingSceneInfo(game.scenes.get(currentScene));
    }

    private void populateFormUsingSceneInfo(Scene scene) {
        sceneIDField.setText(scene.sceneID);
        bgImgField.setText(scene.bgImage);
        bgColorField.setText(scene.bgColor);
    }

    @FXML
    public void sceneIDKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (!sceneIDExists(game, sceneIDField.getText())) {
                game.scenes.get(currentScene).sceneID = sceneIDField.getText();
            }
            else
                sceneIDField.setText(game.scenes.get(currentScene).sceneID);
        }
    }

    @FXML
    public void bgColorKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (colorExists(game, bgColorField.getText()) || bgColorField.getText().strip().isEmpty()) {
                game.scenes.get(currentScene).bgColor = bgColorField.getText().strip();

                // Now refresh grids and reload scene
                refreshCanvas(context);
            }
            else
                bgColorField.setText(game.scenes.get(currentScene).bgColor);
        }
    }

    @FXML
    public void bgImgKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (imgExists(game, bgImgField.getText()) || bgImgField.getText().strip().isEmpty()) {
                game.scenes.get(currentScene).bgImage = bgImgField.getText().strip();

                // Now refresh grids and reload scene
                refreshCanvas(context);
            }
            else
                bgImgField.setText(game.scenes.get(currentScene).bgImage);
        }
    }
}
