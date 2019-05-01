package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.GameObject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import static auth.helpers.DataHelpers.*;
import static auth.helpers.ScreenHelpers.initialiseObjectsGrid;
import static auth.helpers.ScreenHelpers.refreshCanvas;

public class ObjPropsController extends JXMLController {
    private Game game;
    private GameObject selectedObject;

    @FXML
    public TextField objectIDField, widthField, heightField, bgImgField, bgColorField;


    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        selectedObject = getObjectByID(game, context.selectedID);
        populateFormUsingObjectInfo(selectedObject);
    }

    private void populateFormUsingObjectInfo(GameObject object) {
        objectIDField.setText(object.objectID);
        widthField.setText(String.format( "%.2f", object.width));
        heightField.setText(String.format( "%.2f", object.height));
        bgImgField.setText(object.bgImage);
        bgColorField.setText(object.bgColor);
    }

    @FXML
    public void objectIDKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (!objectIDExists(game, objectIDField.getText())) {
                // update all ID references
                updateObjectIDReferences(context, game, selectedObject.objectID, objectIDField.getText());

                selectedObject.objectID = objectIDField.getText();

                // Now refresh grids and reload scene
                initialiseObjectsGrid(context);
                refreshCanvas(context);
            }
            else
                objectIDField.setText(selectedObject.objectID);
        }
    }

    @FXML
    public void widthKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                selectedObject.width = Double.parseDouble(widthField.getText());

                // Now refresh grids and reload scene
                refreshCanvas(context);
            } catch (Exception ex) {
                // Illegal value
                widthField.setText(String.format( "%.2f", selectedObject.width));
            }
        }
    }

    @FXML
    public void heightKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                selectedObject.height = Double.parseDouble(heightField.getText());

                // Now refresh grids and reload scene
                refreshCanvas(context);
            } catch (Exception ex) {
                // Illegal value
                heightField.setText(String.format( "%.2f", selectedObject.height));
            }
        }
    }

    @FXML
    public void bgImageKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if(imgExists(game, bgImgField.getText()) || bgImgField.getText().strip().isEmpty()) {
                selectedObject.bgImage = bgImgField.getText().strip();

                // Now refresh grids and reload scene
                initialiseObjectsGrid(context);
                refreshCanvas(context);
            }
            else
                bgImgField.setText(selectedObject.bgImage);
        }
    }

    @FXML
    public void bgColorKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if(colorExists(game, bgColorField.getText()) || bgColorField.getText().strip().isEmpty()) {
                selectedObject.bgColor = bgColorField.getText().strip();

                // Now refresh grids and reload scene
                initialiseObjectsGrid(context);
                refreshCanvas(context);
            }
            else
                bgColorField.setText(selectedObject.bgColor);
        }
    }
}
