
package Engine.src.EngineData.Components;

/**
 * Component that represents an EngineInstance's basic functionality to display
 * @author David Liu
 */
public class BasicComponent extends Component {
    private double myWidth;
    private double myHeight;
    private double myXPos;
    private double myYPos;
    private String myFilename;
    private int myZIndex;
    private String myLogic;
    private boolean alive;

    /**
     * Initializes the basic functionality of an EngineInstance
     * @param filename string of file resource path to read file
     * @param xPos x position
     * @param yPos y position
     * @param width width
     * @param height height
     */
    public BasicComponent(String filename, String xPos, String yPos, String width, String height) {
        myFilename = filename;
        myXPos = Double.parseDouble(xPos);
        myYPos = Double.parseDouble(yPos);
        myWidth = Double.parseDouble(width);
        myHeight = Double.parseDouble(height);
        myLogic = "";
        myZIndex = 0;
        alive = true;
    }

    /**
     * Initializes the basic functionality of an EngineInstance including z index
     * @param filename string of file resource path to read file
     * @param xPos x position
     * @param yPos y position
     * @param width width
     * @param height height
     * @param zIndex z index
     */
    public BasicComponent(String filename, String xPos, String yPos, String width, String height, String zIndex) {
        this(filename, xPos, yPos, width, height);
        myZIndex = Integer.parseInt(zIndex);
    }

    /**
     * Gives width
     * @return width
     */
    public double getWidth() {
        return myWidth;
    }

    /**
     * Gives height
     * @return height
     */
    public double getHeight() {
        return myHeight;
    }

    /**
     * Sets width
     * @param myWidth new width
     */
    public void setWidth(double myWidth) {
        this.myWidth = myWidth;
    }

    /**
     * Sets height
     * @param myHeight new height
     */
    public void setHeight(double myHeight) {
        this.myHeight = myHeight;
    }

    /**
     * Gives x position
     * @return x position
     */
    public double getX() { return myXPos; }

    /**
     * Gives y position
     * @return y position
     */
    public double getY() { return myYPos; }

    /**
     * Sets x position
     * @param xPos new x position
     */
    public void setX(double xPos) { this.myXPos = xPos; }

    /**
     * Sets y position
     * @param yPos new y position
     */
    public void setY(double yPos) { this.myYPos = yPos; }

    /**
     * Gives resource file pathname
     * @return string resource file pathname
     */
    public String getMyFilename() {
        return myFilename;
    }

    /**
     * Sets string resource file pathname
     * @param filename new string resource file pathname
     */
    public void setMyFile(String filename) {
        this.myFilename = filename;
    }

    /**
     * Gives z index
     * @return z index
     */
    public int getMyZIndex() {
        return myZIndex;
    }

    /**
     * Sets z index
     * @param myZIndex new z index
     */
    public void setMyZIndex(int myZIndex) {
        this.myZIndex = myZIndex;
    }

    /**
     * Gives logic string
     * @return logic string for aiming
     */
    public String getLogic(){
        return myLogic;
    }

    /**
     * Sets logic string
     * @param logic new logic string for aiming
     */
    public void setLogic(String logic) { myLogic = logic; }

    /**
     * EngineInstance alive condition
     * @return whether EngineInstance is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set the EngineInstance alive condition to false
     */
    public void kill(){
        alive = false;
    }

    /**
     * Gives a copy of the BasicComponent
     * @return copy of the BasicComponent
     */
    @Override
    public Component copy() {
        return new BasicComponent(myFilename, Double.toString(myXPos), Double.toString(myYPos), Double.toString(myWidth), Double.toString(myHeight), Integer.toString(myZIndex));
    }
}