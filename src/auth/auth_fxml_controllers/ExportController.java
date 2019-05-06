package auth.auth_fxml_controllers;

import auth.helpers.DataHelpers;
import auth.screens.ExportScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * This class handles the interactions between a user and the export menu. This class contains all of the methods that a
 * JavaFXML file uses when a JavaFX node is interacted with, for example the export() method is linked to an export button.
 *
 * @author Anshu Dwibhashi
 * @author Duc Tran
 */
public class ExportController {
    @FXML
    public TextField titleField, fileShower;
    @FXML
    public TextArea descField;
    @FXML
    public Button imagePickerButton;
    public void ExportController(){

    }

    private Game game; private ExportScreen screen;

    /**
     * Sets the game parameter and screen paramter to this class's game and screen variables.
     *
     * @param game The Game object that contains all of the necessary information for the game.
     * @param screen The ExportScreen object that displays the export menu.
     */
    public void initGame(Game game, ExportScreen screen) {
        this.game = game; this.screen = screen;
    }

    /**
     * This method opens a file explorer to allow the user to select an image to set it as the game's thumbnail.
     *
     * @param action The MouseEvent lets the File Chooser know where the mouse is and what information is selected.
     */
    @FXML
    public void pickImage(MouseEvent action) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        var src = file.getAbsolutePath();
        fileShower.setText(src);
    }

    /**
     * This method opens up a file explorer so the user can select the name and location of the game file.
     *
     * @param action The MouseEvent objects allow the file explorer to know where the mouse is located.
     */
    @FXML
    public void export(MouseEvent action) {
        String contents = new Gson().toJson(game, new TypeToken<Game>(){}.getType());
        try {
            File file = new File(titleField.getText().replaceAll("[^A-Za-z]+", "")+".game");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(contents);
            writer.close();
            String imgPath = fileShower.getText().substring(fileShower.getText().indexOf("/img"));
            DataHelpers.exportGame(imgPath, titleField.getText(), descField.getText(), file.getAbsolutePath());
            screen.closeThis();
        } catch ( Exception e) {e.printStackTrace();}
    }
}
