package Player.src.Regions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class TitleRegion extends Region {
    public static final String TEXT_STR = "Cracking Open a Scrolled One";
    private final String TITLE_LABEL_ID = "titleLabel";

    private GridPane myTitlePane;

    public TitleRegion(String regionID) {
        super(); // this calls buildGroup, so anything that needs to be set must be set in Region()
        myTitlePane = new GridPane();
        myTitlePane.setId(regionID);

        myGroup = new Group();
        buildGroup();
        myTitlePane.add(myGroup, 0, 0);

    }

    protected void buildGroup() {

        Label titleLabel = new Label(TEXT_STR);
        titleLabel.setId(TITLE_LABEL_ID);

        myGroup.getChildren().add(titleLabel);

    }

    public GridPane getPane() {
        return myTitlePane;
    }
}
