package Player.src.Regions;

import javafx.scene.Group;

import java.util.ArrayList;

public abstract class Region {
    Group myGroup;
    protected ArrayList<Region> myComponents;

    public Region() {
        myComponents = new ArrayList<>();
    }

//    public Group getMainComponent() {
//        return myGroup;
//    }

    abstract void buildGroup();

//    public abstract Pane getPane();

//    protected void setBackground(Paint bg, CornerRadii cr, Insets in) {
//        myHBox.setBackground(new Background(new BackgroundFill(bg, cr, in)));
//    }
//
//    protected void setSize(double wd, double ht) {
//        myWidth = wd;
//        myHeight = ht;
//
//        myHBox.setPrefWidth(wd);
//        myHBox.setPrefHeight(ht);
//    }
//
//    protected HBox getHBox() {
//        return myHBox;
//    }

}
