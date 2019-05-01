package uiutils.components;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static auth.RunAuth.bebasKaiMedium;

public class ButtonGenerator {
    public static Group createButton(String content) {
        return createButton(content, false);
    }
    public static Group createButton(String content, boolean small) {
        var button = new Group();
        var background = new Rectangle((small ? 100 : 200), 50);
        background.setFill(Color.GHOSTWHITE);
        var text = TextGenerator.makeTextRelative(content, bebasKaiMedium, Color.web("#333333"),
                background.getWidth() / 2,
                background.getHeight());
        background.setEffect(new DropShadow(10, Color.LIGHTGRAY));
        background.setArcWidth(20);
        background.setArcHeight(20);
        button.getChildren().addAll(background, text);
        button.setCursor(Cursor.HAND);
        return button;
    }
}
