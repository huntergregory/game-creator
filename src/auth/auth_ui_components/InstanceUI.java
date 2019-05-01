package auth.auth_ui_components;

import gamedata.Game;
import gamedata.Instance;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static auth.helpers.ScreenHelpers.*;

public class InstanceUI implements Selectable {
    private Instance instance; private Game game;
    private Node view;
    public InstanceUI (Instance instance, Game game) {
        this.instance = instance; this.game = game;

        try {
            if (!instance.bgImage.isEmpty()) {
                view = new ImageView();
                ((ImageView) view).setFitWidth(instance.width); ((ImageView) view).setFitHeight(instance.height);
                ((ImageView) view).setImage(getImageById(game, instance.bgImage));
                ((ImageView) view).setX(instance.x); ((ImageView) view).setY(instance.y);
            } else {
                setBackgroundColor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setBackgroundColor();
        }
        view.setCursor(Cursor.HAND);
    }

    private  void setBackgroundColor() {
        System.out.println(instance.bgColor);
        view = new Rectangle();
        ((Rectangle) view).setWidth(instance.width); ((Rectangle) view).setHeight(instance.height);
        Color backgroundColor = (instance.bgColor.isEmpty() ? Color.WHITE : getColorByID(game, instance.bgColor));
        ((Rectangle) view).setFill(backgroundColor);
        ((Rectangle) view).setX(instance.x); ((Rectangle) view).setY(instance.y);
    }

    @Override
    public void select() {
        System.out.println("select called");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(15);
        dropShadow.setOffsetY(0);
        dropShadow.setOffsetX(0);
        dropShadow.setColor(Color.web("#00aaff"));
        view.setEffect(dropShadow); selected = true;
    }

    @Override
    public void deselect() {
        System.out.println("deselect called");
        view.setEffect(makeShadow(Color.TRANSPARENT)); selected = false;
    }

    public boolean selected = false;

    public Node getView() {
        return view;
    }
}
