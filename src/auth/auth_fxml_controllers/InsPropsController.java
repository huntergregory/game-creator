package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.Instance;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import static auth.helpers.DataHelpers.*;
import static auth.helpers.ScreenHelpers.initialiseObjectsGrid;
import static auth.helpers.ScreenHelpers.refreshCanvas;

/**
 * This class controls what happens when a user interacts with the Instance property panel after selecting an object.
 * To use this class, have a JavaFXML file and then right a method to link it to a JavaFX element.
 *
 * @author Anshu Dwibhashi
 * @author Duc Tran
 */
public class InsPropsController extends JXMLController {
    private Game game;
    private Instance selectedInstance;

    @FXML
    public TextField instanceIDField, widthField, heightField, bgImgField, bgColorField,
    zIndexField, xField, yField, instanceOfField;


    /**
     * Gets the correct instance of an object so that its information can be properly placed in the panel.
     *
     * @param propsPane The JavaFX Pane that contains all of the Nodes that make up the panel.
     * @param context The current scene that is being worked on.
     */
    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        selectedInstance = getInstanceByID(game, context.selectedID, context.getCurrentScene());
        populateFormUsingInstanceInfo(selectedInstance);
    }

    /**
     * Fills in the instance panel with the information obtained from the Instance object.
     *
     * @param object An Instance of a game object. Contains all of the information it needs to created by the player.
     */
    private void populateFormUsingInstanceInfo(Instance object) {
        instanceIDField.setText(object.instanceID);
        instanceOfField.setText(object.instanceOf);
        widthField.setText(String.format( "%.2f", object.width));
        heightField.setText(String.format( "%.2f", object.height));
        xField.setText(String.format( "%.2f", object.x));
        yField.setText(String.format( "%.2f", object.y));
        zIndexField.setText(String.format( "%d", object.zIndex));
        bgImgField.setText(object.bgImage);
        bgColorField.setText(object.bgColor);
    }

    /**
     * Changes the instance ID of a selected Instance when the enter key is pressed.
     *
     * @param The key being pressed currently.
     */
    @FXML
    public void instanceIDKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (!objectIDExists(game, instanceIDField.getText())) {
                selectedInstance.instanceID = instanceIDField.getText();
            }
            else
                instanceIDField.setText(selectedInstance.instanceID);
        }
    }

    /**
     * Changes the width of a selected instance when the enter key is pressed and refreshes the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void widthKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                selectedInstance.width = Double.parseDouble(widthField.getText());

                // Now refresh grids and reload scene
                refreshCanvas(context);
            } catch (Exception ex) {
                // Illegal value
                widthField.setText(String.format( "%.2f", selectedInstance.width));
            }
        }
    }

    /**
     * Changes the height of a selected instance when the enter key is pressed and refreshes the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void heightKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                selectedInstance.height = Double.parseDouble(heightField.getText());

                // Now refresh grids and reload scene
                refreshCanvas(context);
            } catch (Exception ex) {
                // Illegal value
                heightField.setText(String.format( "%.2f", selectedInstance.height));
            }
        }
    }

    /**
     * Changes the current image of a selected instance if the image exists and once the enter key is pressed. Then
     * refreshes the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void bgImageKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if(imgExists(game, bgImgField.getText()) || bgImgField.getText().strip().isEmpty()) {
                selectedInstance.bgImage = bgImgField.getText().strip();

                // Now refresh grids and reload scene
                initialiseObjectsGrid(context);
                refreshCanvas(context);
            }
            else
                bgImgField.setText(selectedInstance.bgImage);
        }
    }

    /**
     * Changes the background color of a selected instance once the enter key is pressed and if the color exists. Then
     * refreshes the canvas to show the change.
     *
     * @param e The curren key being pressed.
     */
    @FXML
    public void bgColorKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if(colorExists(game, bgColorField.getText()) || bgColorField.getText().strip().isEmpty()) {
                selectedInstance.bgColor = bgColorField.getText().strip();

                // Now refresh grids and reload scene
                initialiseObjectsGrid(context);
                refreshCanvas(context);
            }
            else
                bgColorField.setText(selectedInstance.bgColor);
        }
    }

    /**
     * Changes the y-location of an instance once the enter key is pressed and if the location is valid. Then refreshes
     * the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void yKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (Double.valueOf(yField.getText()) >= 0 && Double.valueOf(yField.getText()) <= 3000) {
                    selectedInstance.y = Double.valueOf(yField.getText().strip());
                    refreshCanvas(context);
                } else {
                    yField.setText(selectedInstance.y+"");
                }
            } catch(Exception ex) {
                yField.setText(selectedInstance.y+"");
            }
        }
    }

    /**
     * Changes the x-location of an instance once the enter key is pressed and if the location is valid. Then refreshes
     * the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void xKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (Double.valueOf(xField.getText()) >= 0 && Double.valueOf(xField.getText()) <= 3000) {
                    selectedInstance.x = Double.valueOf(xField.getText().strip());
                    refreshCanvas(context);
                } else {
                    xField.setText(selectedInstance.x + "");
                }
            } catch (Exception ex) {
                xField.setText(selectedInstance.x+"");
            }
        }
    }

    /**
     * Changes Changes the y-location of an instance once the enter key is pressed and if the location is valid. Then refreshes
     * the canvas to show the change.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void zIndexKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (Integer.valueOf(zIndexField.getText()) >= 1) {
                    selectedInstance.zIndex = Integer.valueOf(zIndexField.getText().strip());
                    refreshCanvas(context);
                } else {
                    zIndexField.setText(selectedInstance.zIndex+"");
                }
            } catch (Exception ex) {
                zIndexField.setText(selectedInstance.zIndex+"");
            }
        }
    }
}
