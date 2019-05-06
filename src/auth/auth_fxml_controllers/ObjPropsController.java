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

/**
 * This class controls what happens when a user interacts with the GameObject property panel after selecting an object.
 * To use this class, when a JavaFX element is interacted with, call a method that is linked to that interaction.
 *
 * @author Anshu Dwibhashi
 * @author Duc Tran
 */
public class ObjPropsController extends JXMLController {
    private Game game;
    private GameObject selectedObject;

    @FXML
    public TextField objectIDField, widthField, heightField, bgImgField, bgColorField;

    /**
     * Sets the correct canvas to find the correct objects. Then selects the correct object to display in the pane.
     *
     * @param propsPane The property Pane of an object.
     * @param context The current canvas to be displayed.
     */
    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        selectedObject = getObjectByID(game, context.selectedID);
        populateFormUsingObjectInfo(selectedObject);
    }

    /**
     * Gets the property information of a GameObject and then displays them in a pane.
     *
     * @param object The GameObject selected.
     */
    private void populateFormUsingObjectInfo(GameObject object) {
        objectIDField.setText(object.objectID);
        widthField.setText(String.format( "%.2f", object.width));
        heightField.setText(String.format( "%.2f", object.height));
        bgImgField.setText(object.bgImage);
        bgColorField.setText(object.bgColor);
    }

    /**
     * Changes object ID of a Game Object to be the current string in the text field once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
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

    /**
     * Changes the initial width of an Game Object once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
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

    /**
     * Changes the initial height of a Game Object to the height in the text field once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
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

    /**
     * Changes the initial image of a Game Object to a select image if the image exists and once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
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

    /**
     * Changes the initial height of the Game Object once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
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
