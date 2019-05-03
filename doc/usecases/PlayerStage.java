//Example 4: User favorites a game

import java.util.ArrayList;
import java.util.List;
public class PlayerStage{

    List<String> myFavorites = new ArrayList<>();
    // myMap is a map of games to their player_images that already exists

    public void buildGroup() {
        placeHeader(true); // adds "Favorites" header
        placeThumbnails(true); // adds "Favorites" thumbnails, should be null
        placeHeader(false); // adds "All Games" header
        placeHeader(false); // adds all games' thumbnails
        // this adds all of these to the gamesRegion myPane variable
    }

    gameOneThumbnail.onMouseClicked(updateFavorites(gameOne, true)); // true = add to favorites

    public void updateGroup(String game, boolean add) {
        if (add) {
            myFavorites.add(game);
        } else {
            myFavorites.remove(game);
        }
        myPane.getChildren().removeAll();
        buildGroup();
    }

    public void placeHeader(boolean b){

    }

    public void placeThumbnails(boolean b){

    }

}