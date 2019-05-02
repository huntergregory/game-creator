package Player.Regions;

import Player.PlayerMain.PlayerStage;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.Properties;

public class DescriptionRegion extends Region {

    public static final double INVITATION_HEIGHT = 20;
    public static final double OFFSET = 10;

    private Properties myStyles;
    private Image myImage;
    private ScrollPane myDescPane;
    private PlayerStage myContext;

    public DescriptionRegion(String regionID, PlayerStage context) {
        super();
        this.myContext = context;

        myDescPane = new ScrollPane();
        myDescPane.setId(regionID);

        myGroup = new Group();
        buildGroup();
        myDescPane.setContent(myGroup);

    }

    public void updateRegion(String game) {

        System.out.println("updating region");
        myGroup.getChildren().clear();
        Description description = new Description(game, myContext);

        myGroup.getChildren().add(description.getPane());
        myDescPane.setContent(myGroup);

    }

    // TODO: unhardcode this
    protected void buildGroup() {
        // this will be what the region looks like initially

        GridPane grid = new GridPane();
        Label invitation = new Label("Click on a game from the Games menu!");

        grid.add(invitation, 0, 0, 1, 1);
        myGroup.getChildren().add(grid);

    }

    public ScrollPane getPane() {
        return myDescPane;
    }

}
