package Engine.src.Components;

import java.io.File;

public class BasicComponent extends Component {
    private double myWidth;
    private double myHeight;
    private double myXPos;
    private double myYPos;
    private String myFilename;
    private int myZIndex;

    public BasicComponent(String filename, double xPos, double yPos, double width, double height, int zIndex) {
        myFilename = filename;
        myXPos = xPos;
        myYPos = yPos;
        myWidth = width;
        myHeight = height;
        myZIndex = zIndex;
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
}