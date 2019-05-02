
package Engine.src.EngineData.Components;

public class BasicComponent extends Component {
    private double myWidth;
    private double myHeight;
    private double myXPos;
    private double myYPos;
    private String myFilename;
    private String myColor;
    private String myID;
    private int myZIndex;
    private String myLogic;

    public BasicComponent(String filename, String xPos, String yPos, String width, String height) {
        myFilename = filename;
        myXPos = Double.parseDouble(xPos);
        myYPos = Double.parseDouble(yPos);
        myWidth = Double.parseDouble(width);
        myHeight = Double.parseDouble(height);
        myLogic = "";
        myZIndex = 0;
    }

    public BasicComponent(String filename, String xPos, String yPos, String width, String height, String zIndex) {
        this(filename, xPos, yPos, width, height);
        myZIndex = Integer.parseInt(zIndex);
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

    public String getLogic(){
        return myLogic;
    }

    public void setLogic(String logic) { myLogic = logic; }
}