package Player.src.Regions;

import Player.src.Features.Controls.*;
import Player.src.PlayerMain.PlayerStage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.Region;

import java.util.HashMap;
import java.util.Map;

public class Description {

    private final String OPTIONS_ID = "options";
    private final String BUTTONS_ID = "buttonsBox";
    private final String DESCRIPTION_ID = "descGrid";
    public final static double IMAGE_WIDTH = 365;
    public final static double IMAGE_HEIGHT = 180;
    public final static double DESC_WIDTH = IMAGE_WIDTH;
    public final static double DESC_HEIGHT = 200;

    private Map<String, String> myImageMap;
    private Map<String, String> myDescMap;

    private String myGame;
    private GridPane myPane;
    private ImageView myImage;
    private Label myDescription;
    private HBox myGameOptions;
    private PlayerStage myContext;

    public Description(String game, PlayerStage context) {
        this.myContext = context;

        // TODO: store this info differently---could be done in ResourceBundle or JSON
        myImageMap = new HashMap<>();
        myImageMap.put("Flappy Bird", "/img/flappy-bird.png");
        myImageMap.put("Mario", "/img/mario.jpg");
        myImageMap.put("Metroid", "/img/metroid.png");
        myImageMap.put("Doodle Jump", "/img/doodle-jump.jpg");

        myDescMap = new HashMap<>();
        myDescMap.put("Flappy Bird", "a bird and it flies pipes jump whoo");
        myDescMap.put("Mario", "it's a me mario");
        myDescMap.put("Metroid", "did you know samus is a girl ??");
        myDescMap.put("Doodle Jump", "ya ya yeet");

        myGame = game;

        myPane = new GridPane();
        myPane.setId(DESCRIPTION_ID);

        placeImage();
        placeDescription();
        placeGameOptions();

    }

    private void placeImage() {

        myImage = new ImageView(new Image(this.getClass().getResourceAsStream(myImageMap.get(myGame))));
        myImage.setFitHeight(IMAGE_HEIGHT);
        myImage.setFitWidth(IMAGE_WIDTH);
        myPane.add(myImage, 0, 0, 2, 1);

    }

    private void placeDescription() {

        myDescription = new Label(myDescMap.get(myGame));
        myPane.add(myDescription, 0, 1, 2, 1);

    }

    // TODO: add Play, Edit, Rate, Rating, High Scores
    private void placeGameOptions() {
        myGameOptions = new HBox();
        myGameOptions.setId(OPTIONS_ID);

        var leftPadding = new Region();
        var options = new VBox();
        options.setId(BUTTONS_ID);
        var rightPadding = new Region();

        Button run = new Run(myContext, myGame).getButton();
        Button edit = new Edit(myContext, myGame).getButton();
        Button rate = new Rate(myContext, myGame).getButton();

        options.getChildren().addAll(run, edit, rate);
        myGameOptions.setHgrow(leftPadding, Priority.ALWAYS);
        myGameOptions.setHgrow(rightPadding, Priority.ALWAYS);

        myGameOptions.getChildren().addAll(leftPadding, options, rightPadding);
        myPane.add(myGameOptions, 2, 0, 1, 2);
    }


    protected GridPane getPane() {
        return myPane;
    }
}
