package gamedata;

import javafx.scene.paint.Color;

@Deprecated
public class Instance {
    public double x, y, width, height;
    public int zIndex;
    public String instanceID, instanceLogic, bgImage, bgColor, instanceOf;
    public Instance() {
        instanceOf = "";
        instanceID = "";
        bgColor = "";
        bgImage = "";
        instanceLogic = "";
    }
}
