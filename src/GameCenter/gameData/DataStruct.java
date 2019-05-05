package GameCenter.gameData;

import java.io.FileNotFoundException;

/**
 * DataStruct holds necessary information about each game.
 *
 * To be used in conjunction with DataParser and DataWriter.
 *
 * @author Januario Carreiro
 */
public class DataStruct {
    private String name, imagePath, description, sourcePath, rating, favorite;
    private DataWriter dataWriter = new DataWriter();

    /**
     * Constructor for DataStruct
     *
     * @param n name of game
     * @param i path of image of game's thumbnail
     * @param d description of game
     * @param s path of game's source code
     * @param r rating of game
     * @param f whether game is a favorite or not
     */
    public DataStruct(String n, String i, String d, String s, String r, String f) {
        this.name = n;
        this.imagePath = i;
        this.description = d;
        this.sourcePath = s;
        this.rating = r;
        this.favorite = f;
    }

    /**
     * Returns name passed to the constructor
     */
    public String getName() { return name; }

    /**
     * Returns path of image passed to the constructor
     */
    public String getImagePath() { return imagePath; }

    /**
     * Returns description passed to the constructor
     */
    public String getDescription() { return description; }

    /**
     * Returns path of game's source code passed to the constructor
     */
    public String getSourcePath() { return sourcePath; }

    public double getRating() {
        return Double.parseDouble(rating);
    }

    /**
     * Returns favorite status of game
     */
    public boolean getFavorite() { return Boolean.parseBoolean(favorite); }

    /**
     * Sets game's rating in the .json file using DataWriter and updates rating variable.
     *
     * @param value new rating
     * @param gameIndex which game's rating is to be updated
     */
    public void setRating(double value, int gameIndex) {
        rating = String.valueOf(value);
        try {
            dataWriter.writeRating(rating, gameIndex);
        } catch (FileNotFoundException e) {
            // should not occur
        }
    }

    /**
     * Sets game's favorite status in the .json file using DataWriter and updates favorite variable.
     *
     * @param value new favorite status
     * @param gameIndex which game's favorite status is to be updated
     */
    public void setFavorite(boolean value, int gameIndex) {
        favorite = String.valueOf(value);
        try {
            dataWriter.writeFavorite(favorite, gameIndex);
        } catch (FileNotFoundException e) {
            // should not occur
        }
    }
}
