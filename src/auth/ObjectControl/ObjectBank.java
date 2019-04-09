package auth.ObjectControl;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectBank {
    private List<Map<String, String>> myObjects;
    private ObjectManager myManager;

    public ObjectBank(ObjectManager manager){
        myObjects = new ArrayList<>();
        myManager = manager;
        Map<String, String> testObject = new HashMap<>();
//        testObject.put("ImageFile", "C:\\Users\\ianha\\OneDrive\\Pictures\\profileImage.png");
//        testObject.put("ObjectName", "Test");
//        addObject(testObject);
    }

    public VBox getView(){
        VBox totalView = new VBox();
        ScrollPane objectScroller = new ScrollPane();
        VBox allObjects = new VBox();
        Label bankLabel = new Label("Object Bank");
        bankLabel.getStyleClass().add("properties-label");
        try {
            for (Map<String, String> object : myObjects) {
                HBox objectRow = new HBox();
                Image objectImage = null;
                try {
                    objectImage = new Image(new FileInputStream(object.get("ImageFile")));
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                ImageView objectImageView = new ImageView(objectImage);
                objectImageView.setPreserveRatio(true);
                objectImageView.setFitHeight(30);
                Text objectName = new Text(object.get("ObjectName"));
                objectName.setFill(Color.WHITE);
                objectRow.getStyleClass().add("bank-row");
                objectRow.getChildren().addAll(objectImageView, objectName);
                allObjects.getChildren().add(objectRow);
            }
            objectScroller.setContent(allObjects);
        }
        catch(NullPointerException e){
            System.out.println("Invalid file path");
        }
        totalView.getChildren().addAll(bankLabel, objectScroller);
        return totalView;
    }

    public void addObject(Map<String, String> object){
        myObjects.add(object);
        getView();
    }
}
