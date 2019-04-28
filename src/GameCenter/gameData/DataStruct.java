package GameCenter.gameData;

import java.io.FileNotFoundException;

public class DataStruct {
    private String name, imagePath, description, sourcePath, rating, favorite;
    private DataWriter dataWriter = new DataWriter();

    DataStruct(String n, String i, String d, String s, String r, String f) {
        name = n;
        imagePath = i;
        description = d;
        sourcePath = s;
        rating = r;
        favorite = f;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public double getRating() {
        return Double.parseDouble(rating);
    }

    public boolean getFavorite() { return Boolean.parseBoolean(favorite);}

    public void setRating(double value, int gameIndex) {
        rating = String.valueOf(value);
        try {
            dataWriter.writeRating(rating, gameIndex);
        } catch (FileNotFoundException e) {
            // This should never happen
        }
    }

    public void setFavorite(boolean value, int gameIndex) {
        favorite = String.valueOf(value);
        try {
            dataWriter.writeFavorite(favorite, gameIndex);
        } catch (FileNotFoundException e) {
            // this should never happen
        }
    }
}
