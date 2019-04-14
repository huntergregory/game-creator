package Player.src.Regions;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.File;

public class Thumbnail {
    private String myName;
    private StackPane myPaneRoot;
    private ImageView myImage;
    private double myWidth;
    private double myHeight;

    public Thumbnail(String gameName, String filename, double wd, double ht) {

        myName = gameName;
        File file = new File(filename);
        myImage = new ImageView();
        try {
            myImage = new ImageView(file.toURI().toString());
        } catch (Exception e) {
            //TODO: better exception handling
            System.out.println("Error occurred when trying to set an image for game thumbnail");
        }

        myPaneRoot = new StackPane();
        myWidth = wd;
        myHeight = ht;

        makeThumbnail();
    }

    public String getName() {
        return myName;
    }

    private void makeThumbnail() {
        myImage.setFitWidth(myWidth);
        myImage.setFitHeight(myHeight);
        myPaneRoot.getChildren().add(myImage);
    }

    public StackPane getPane() {
        return myPaneRoot;
    }
}
