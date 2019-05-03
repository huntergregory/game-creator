package auth.auth_fxml_controllers;

import auth.helpers.DataHelpers;
import auth.screens.ExportScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gamedata.Game;
import gamedata.Resource;
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
import java.security.MessageDigest;

import static auth.helpers.ScreenHelpers.initialiseImagesGrid;
import static gamedata.Resource.ResourceType.IMAGE_RESOURCE;

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

    public void initGame(Game game, ExportScreen screen) {
        this.game = game; this.screen = screen;
    }

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
