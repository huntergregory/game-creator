package ez_engine;

import gamedata.Game;
import gamedata.Instance;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

import static auth.Dimensions.*;
import static auth.helpers.ScreenHelpers.*;

public class RenderingHelpers {
    private static Game game;
    private static Map<String, Pane> instanceMap = new HashMap<>();

    public static void initGame(Game g) {
        game = g;
    }
    public static Scene renderScene(gamedata.Scene scene) {
        var parentGroup = new Pane(); parentGroup.setPrefWidth(CANVAS_WIDTH); parentGroup.setPrefHeight(CANVAS_HEIGHT);
        Scene toReturn = new Scene(parentGroup);
        setBg(parentGroup, scene.bgColor, scene.bgImage);

        for (Instance i : scene.instances) {
            parentGroup.getChildren().add(renderInstance(i));
        }

        return toReturn;
    }

    private static void setBg(Pane pane, String bgColor, String bgImage) {
        if (bgColor != null) {
            BackgroundFill myBF = new BackgroundFill(getColorByID(game, bgColor), new CornerRadii(0),
                    new Insets(0.0,0.0,0.0,0.0));// or null for the padding
            pane.setBackground(new Background(myBF));

        } else if (bgImage != null) {
            BackgroundImage myBI= new BackgroundImage(getImageById(game, bgImage),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            pane.setBackground(new Background(myBI));
        }
    }

    public static Pane renderInstance(Instance i) {
        var pane = new Pane();
        pane.setLayoutX(i.x); pane.setLayoutY(i.y); pane.setViewOrder(i.zIndex);
        pane.setPrefWidth(i.width); pane.setPrefHeight(i.height);
        setBg(pane, i.bgColor, i.bgImage);

        instanceMap.put(i.instanceID, pane);

        return pane;
    }
}
