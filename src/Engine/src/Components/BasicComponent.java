package Engine.src.Components;

public class BasicComponent extends Component {
    private double myWidth;
    private double myHeight;
    private double myXPos;
    private double myYPos;
    private String myFilename;
    private int myZIndex;
<<<<<<< HEAD:src/Engine/src/EngineData/Components/BasicComponent.java
    private String myLogic;
    private boolean alive;
=======
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/BasicComponent.java

    public BasicComponent(String filename, double xPos, double yPos, double width, double height, int zIndex) {
        myFilename = filename;
<<<<<<< HEAD:src/Engine/src/EngineData/Components/BasicComponent.java
        myXPos = Double.parseDouble(xPos);
        myYPos = Double.parseDouble(yPos);
        myWidth = Double.parseDouble(width);
        myHeight = Double.parseDouble(height);
        myLogic = "";
        myZIndex = 0;
        alive = true;
=======
        myXPos = xPos;
        myYPos = yPos;
        myWidth = width;
        myHeight = height;
        myZIndex = zIndex;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/BasicComponent.java
    }

    public BasicComponent(String filename, double xPos, double yPos, double width, double height) {
        myFilename = filename;
        myXPos = xPos;
        myYPos = yPos;
        myWidth = width;
        myHeight = height;

    }


    public double getWidth() {
        return myWidth;
    }

    public double getHeight() {
        return myHeight;
    }

    public void setWidth(double myWidth) {
        this.myWidth = myWidth;
    }

    public void setHeight(double myHeight) {
        this.myHeight = myHeight;
    }

    public double getX() { return myXPos; }

    public double getY() { return myYPos; }

    public void setX(double xPos) { this.myXPos = xPos; }

    public void setY(double yPos) { this.myYPos = yPos; }

    public String getMyFilename() {
        return myFilename;
    }

    public void setMyFile(String filename) {
        this.myFilename = filename;
    }

    public int getMyZIndex() {
        return myZIndex;
    }

    public void setMyZIndex(int myZIndex) {
        this.myZIndex = myZIndex;
    }
<<<<<<< HEAD:src/Engine/src/EngineData/Components/BasicComponent.java

    public String getLogic(){
        return myLogic;
    }

    public void setLogic(String logic) { myLogic = logic; }

    public boolean isAlive() {
        return alive;
    }

    public void kill(){
        alive = false;
    }

    @Override
    public Component copy() {
        return new BasicComponent(myFilename, Double.toString(myXPos), Double.toString(myYPos), Double.toString(myWidth), Double.toString(myHeight), Integer.toString(myZIndex));
    }
=======
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/BasicComponent.java
}