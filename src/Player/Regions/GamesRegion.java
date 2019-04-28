package Player.Regions;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class GamesRegion extends Region {

//    public static final double THUMB_WD = 300;
//    public static final double THUMB_HT = 150;
    public static final double OFFSET = 10;
    public static final double HEADER_HEIGHT = 15;
    public static final double SCROLLBAR_WIDTH = 20;

    private final String PANEL_ID = "gamesBox";

    private ArrayList<Thumbnail> myThumbnails;
    private ScrollPane myGamesPane;
    private double myThumbnailWidth;
    private double myThumbnailHeight;
    private VBox myPanels;
    private HashMap<String, String> myMap;
    private ArrayList<String> myFavorites;

    public GamesRegion(String regionID) {
        super();

        myGamesPane = new ScrollPane();
        myGamesPane.setId(regionID);

        // TODO: Do not hardcode this

        myThumbnailWidth = 380 - (2 * OFFSET) - SCROLLBAR_WIDTH; // -15 to account for the scrollbar which is 15
        myThumbnailHeight = myThumbnailWidth / 2;

//        buildLists();
//        buildGroup();

        myMap = new HashMap<>();
        myFavorites = new ArrayList<>();
        buildGamesMap(); // builds initial games list and adds initial favorites (but initial favs is not gonna be a thing)

        myGroup = new Group();
        buildGroup();
        // initial setting of content
        myGamesPane.setContent(myGroup);


    }

    // TODO: gamesRegion should update whenever we add to favorites/remove from favorites

    private void buildGamesMap() {
        myMap.put("Flappy Bird", "src/Player/res/images/flappy-bird.png");
        myMap.put("Mario", "src/Player/res/images/mario.jpg");
        myMap.put("Metroid", "src/Player/res/images/metroid.png");
        myMap.put("Doodle Jump", "src/Player/res/images/doodle-jump.jpg");

        myFavorites.add("Doodle Jump");
    }

    protected void buildGroup() {

        myPanels = new VBox();
        myPanels.setId(PANEL_ID);

        myThumbnails = new ArrayList<>();

        myPanels.getChildren().add(makeHeader(true));
        myPanels.getChildren().addAll(makeThumbnails(true));
        myPanels.getChildren().add(makeHeader(false));
        myPanels.getChildren().addAll(makeThumbnails(false));

        myGroup.getChildren().add(myPanels);

    }

    // TODO: unhardcode this
    protected HBox makeHeader(boolean favorite) {

        HBox header = new HBox();
//        Rectangle region = new Rectangle(OFFSET, HEADER_HEIGHT, Color.TRANSPARENT);
//        header.getChildren().add(region);

        Label label = new Label();

        if (favorite) {
            if (myFavorites.size() == 0) {
                label.setText("No favorites");
            } else {
                label.setText("Favorites (" + myFavorites.size() + ")");
            }
        } else {
            if (myMap.keySet().size() == 0) {
                label.setText("No games");
            } else {
                int number = myMap.keySet().size();
                label.setText("Games (" + number + ")");
            }
        }

        label.setLayoutX(OFFSET);
        header.getChildren().add(label);

        return header;
    }

    protected ArrayList<StackPane> makeThumbnails(boolean favorite) {
        ArrayList<StackPane> thumbnails = new ArrayList<>();

        ArrayList<String> games = new ArrayList<>();
        if (favorite) {
            games.addAll(myFavorites);
        } else {
            games.addAll(myMap.keySet());
        }

//        myThumbnails = new ArrayList<>();

        for (int i = 0; i < games.size(); i ++) {
            String gameName = games.get(i);
            String filename = myMap.get(gameName);
            Thumbnail thumbnail = new Thumbnail(gameName, filename, myThumbnailWidth, myThumbnailHeight);
            myThumbnails.add(thumbnail);
            StackPane thumbPane = thumbnail.getPane();
            thumbnails.add(thumbPane);
        }

        return thumbnails;
    }

    public ArrayList<Thumbnail> getThumbnails() {
        return myThumbnails;
    }

    public ScrollPane getPane() {
        return myGamesPane;
    }

}
