package GameCenter.gameData;

public class DataStruct {
    private String name, imagePath, description, sourcePath;

    DataStruct(String n, String i, String d, String s) {
        name = n;
        imagePath = i;
        description = d;
        sourcePath = s;
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
}
