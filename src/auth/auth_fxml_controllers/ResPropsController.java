package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.util.Map;

import static auth.helpers.DataHelpers.*;
import static auth.helpers.ScreenHelpers.*;
import static java.util.Map.entry;

/**
 * This Class manages the interactions between the user and the resource pane. To use this class, write a method that should
 * be called when a JavaFX element in interacted with and then link it to that interaction.
 *
 * @author Anshu Dwibhashi
 * @author Duc Tran
 */
public class ResPropsController extends JXMLController{
    private Game game;
    private String selectedID;
    private Class selectedType;

    private Map<Class, Resource.ResourceType> map = Map.ofEntries(
            entry(Image.class, Resource.ResourceType.IMAGE_RESOURCE),
            entry(AudioClip.class, Resource.ResourceType.AUDIO_RESOURCE),
            entry(Color.class, Resource.ResourceType.COLOR_RESOURCE)
    );

    @FXML
    public TextField resourceIDField, srcField;

    private Resource selectedResource;

    /**
     * Calls the JXMLController's initData method, then this method selects the correct Resource object to display the
     * correct information in the resource info panel.
     *
     * @param propsPane The Pane to be in focus.
     * @param context The current canvas being displayed.
     */
    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        selectedID = context.selectedID;
        selectedType = context.selectedType;
        selectedResource = getResourceByType(game, selectedID, map.get(selectedType));
        populateFormUsingResourceInfo(selectedResource);
    }

    /**
     * Populates the resource information panel with the information from the resource parameter.
     *
     * @param resource The Resource object that was selected.
     */
    private void populateFormUsingResourceInfo(Resource resource) {
        resourceIDField.setText(resource.resourceID);
        if (resource.src.startsWith("0x")) {
            // It's a colour, show hex code
            srcField.setText("#"+resource.src.substring(resource.src.indexOf("0x")+2));
        } else {
            if (resource.src.contains("/")) {
                srcField.setText(resource.src.substring(resource.src.lastIndexOf("/")+1));
            } else {
                srcField.setText(resource.src);
            }
        }
    }

    /**
     * Changes the resourceID of the selected resource to the current string in the text field once the enter key is pressed.
     *
     * @param e The current key being pressed.
     */
    @FXML
    public void resourceIDKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (!resourceIDExists(game, resourceIDField.getText(), map.get(selectedType))) {
                // update all ID references
                if (selectedResource.resourceType.equals(Resource.ResourceType.IMAGE_RESOURCE) ||
                selectedResource.resourceType.equals(Resource.ResourceType.COLOR_RESOURCE)) {
                    updateResourceIDReferences(context, game, selectedResource.resourceID, resourceIDField.getText(), map.get(selectedType));
                }
                selectedResource.resourceID = resourceIDField.getText();

                // Now refresh grids and reload scene
                switch (map.get(selectedType)) {
                    case IMAGE_RESOURCE:
                        initialiseImagesGrid(context);
                        break;
                    case COLOR_RESOURCE:
                        initialiseColorGrid(context);
                        break;
                    case AUDIO_RESOURCE:
                        initialiseAudioGrid(context);
                        break;
                }
                refreshCanvas(context);
            }
            else
            resourceIDField.setText(selectedResource.resourceID);
        }
    }
}
