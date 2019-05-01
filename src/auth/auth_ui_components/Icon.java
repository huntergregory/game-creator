package auth.auth_ui_components;

import auth.Callback;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static auth.helpers.ScreenHelpers.makeShadow;
import static auth.helpers.ScreenHelpers.makeShadowSelected;

public abstract class Icon implements Selectable {
    private Group view;
    private Circle bgCircle;
    private ImageView icon;

    private boolean selected = false, selectable = true;

    protected void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    @Override
    public void select() {
        if (selectable) {
            if (view.getChildren().contains(bgCircle)) {
                // Either color/tool
                bgCircle.setEffect(makeShadowSelected());
            } else {
                view.setEffect(makeShadowSelected());
            }
            selected = true;
        }
    }

    @Override
    public void deselect() {
        if (selectable) {
            if (view.getChildren().contains(bgCircle)) {
                // Either color/tool
                bgCircle.setEffect(makeShadow());
            } else {
                view.setEffect(makeShadow());
            }
            selected = false;
        }
    }

    public static final double BG_CIRCLE_RADIUS = 25;
    public static final double ICON_SIZE = 30;
    private static final Color BG_COLOUR = Color.WHITE;

    public Icon(Image img, String tooltipText, Callback onClickCallback) {
        view = new Group();
        makeBgCircle(false);
        addBgImage(img);
        addTooltip(tooltipText);
        setOnClickListener(onClickCallback);
    }

    public Icon(Color color, String tooltipText, Callback onClickCallback) {
        view = new Group();
        makeBgCircle();
        setBgCircleColor(color);
        addTooltip(tooltipText);
        setOnClickListener(onClickCallback);
    }

    public Icon(String iconID, String tooltipText, Callback onClickCallback) {
        view = new Group();
        makeBgCircle();
        addImgIcon(iconID);
        addTooltip(tooltipText);
        setOnClickListener(onClickCallback);
    }

    private void setBgCircleColor(Color color) {
        bgCircle.setFill(color);
    }

    private void addBgImage(Image img) {
        var imgView = new ImageView(img);
        imgView.setFitWidth(2*BG_CIRCLE_RADIUS);
        imgView.setFitHeight(2*BG_CIRCLE_RADIUS);
        imgView.setClip(bgCircle);
        imgView.setEffect(makeShadow());
        imgView.setCursor(Cursor.HAND);
        view.getChildren().add(imgView);

    }

    public Group getView() {
        return view;
    }

    private void addTooltip(String tooltipText) {
        Tooltip t = new Tooltip(tooltipText);
        Tooltip.install(view, t);
    }

    private void setOnClickListener(Callback callback) {
        view.setOnMouseClicked(e -> {
            callback.onCallback(Icon.this);
        });
    }

    private void makeBgCircle(boolean add) {
        bgCircle = new Circle();
        bgCircle.setRadius(BG_CIRCLE_RADIUS);
        bgCircle.setFill(BG_COLOUR);
        bgCircle.setCursor(Cursor.HAND);
        bgCircle.setCenterY(BG_CIRCLE_RADIUS); bgCircle.setCenterX(BG_CIRCLE_RADIUS);
        bgCircle.setEffect(makeShadow());
        if (add)
            view.getChildren().add(bgCircle);
    }

    private void makeBgCircle() {
        makeBgCircle(true);
    }

    private void addImgIcon(String iconID) {
        icon = new ImageView(new Image(ToolIcon.class.getResourceAsStream("/icons/"+iconID+".png")));
        icon.setFitHeight(ICON_SIZE); icon.setFitWidth(ICON_SIZE);
        icon.setX(BG_CIRCLE_RADIUS - ICON_SIZE/2); icon.setY(BG_CIRCLE_RADIUS - ICON_SIZE/2);
        icon.setCursor(Cursor.HAND);

        view.getChildren().add(icon);
    }


}
