package ez_engine;

import gamedata.Instance;
import javafx.scene.layout.Pane;

import static ez_engine.RenderingHelpers.setBg;

public class InstanceView {
    private Instance instance;
    private Pane view;

    public InstanceView(Instance instance, Pane view) {
        this.instance = instance; this.view = view;
    }

    // The following methods are basically shorthands for JavaFX methods for Groovy scripts
    public double getX() {
        return view.getLayoutX();
    }

    public double getY() {
        return view.getLayoutY();
    }

    public double getWidth() {
        return view.getWidth();
    }

    public double getHeight() {
        return view.getHeight();
    }

    public void setX(double x) {
        instance.x = x;
        view.setLayoutX(x);
    }

    public void setY(double y) {
        instance.y = y;
        view.setLayoutY(y);
    }

    public void setWidth(double width) {
        view.setPrefWidth(width);
        instance.width = width;
        setBg(view, instance.bgColor, instance.bgImage, width, instance.height);
    }

    public void setHeight(double height) {
        view.setPrefHeight(height);
        instance.height = height;
        setBg(view, instance.bgColor, instance.bgImage, instance.width, height);
    }
}
