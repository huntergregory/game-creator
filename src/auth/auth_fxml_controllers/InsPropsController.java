package auth.auth_fxml_controllers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Instance;
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

import static auth.Dimensions.CANVAS_HEIGHT;
import static auth.Dimensions.CANVAS_WIDTH;
import static auth.helpers.DataHelpers.*;
import static auth.helpers.ScreenHelpers.*;
import static java.util.Map.entry;

public class InsPropsController extends JXMLController {
    private Game game;
    private Instance selectedInstance;

    @FXML
    public TextField instanceIDField, widthField, heightField, bgImgField, bgColorField,
    zIndexField, xField, yField, instanceOfField;


    @Override
    public void initData(Pane propsPane, CanvasScreen context) {
        super.initData(propsPane, context);
        game = context.getGame();
        selectedInstance = getInstanceByID(game, context.selectedID, context.getCurrentScene());
        populateFormUsingInstanceInfo(selectedInstance);
    }

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

    @FXML
    public void yKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (Double.valueOf(yField.getText()) >= 0 && Double.valueOf(yField.getText()) <= CANVAS_HEIGHT) {
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

    @FXML
    public void xKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (Double.valueOf(xField.getText()) >= 0 && Double.valueOf(xField.getText()) <= CANVAS_WIDTH) {
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
