package ez_engine;

import gamedata.Game;
import gamedata.Instance;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

import static auth.Dimensions.CANVAS_HEIGHT;
import static auth.Dimensions.CANVAS_WIDTH;
import static auth.helpers.ScreenHelpers.getColorByID;
import static auth.helpers.ScreenHelpers.getImageById;

public class RenderingHelpers {
    private static Game game;
    private static Map<String, InstanceView> instanceMap = new HashMap<>();

    public static void initGame(Game g) {
        game = g;
    }
    public static Scene renderScene(gamedata.Scene scene) {
        var parentGroup = new Pane(); parentGroup.setPrefWidth(CANVAS_WIDTH); parentGroup.setPrefHeight(CANVAS_HEIGHT);
        Scene toReturn = new Scene(parentGroup);
        setBg(parentGroup, scene.bgColor, scene.bgImage, CANVAS_WIDTH, CANVAS_HEIGHT);

        for (Instance i : scene.instances) {
            parentGroup.getChildren().add(renderInstance(i));
        }

        return toReturn;
    }

    public static void setBg(Pane pane, String bgColor, String bgImage, double fitWidth, double fitHeight) {
        if (bgColor != null && !bgColor.isEmpty()) {
            BackgroundFill myBF = new BackgroundFill(getColorByID(game, bgColor), new CornerRadii(0),
                    new Insets(0.0,0.0,0.0,0.0));
            pane.setBackground(new Background(myBF));

        }
        if (bgImage != null && !bgImage.isEmpty()) {
            pane.getChildren().clear();
            var imgView = new ImageView(getImageById(game, bgImage));
            imgView.setFitHeight(fitHeight); imgView.setFitWidth(fitWidth);
            pane.getChildren().add(imgView);
        }
    }

    public static Pane renderInstance(Instance i) {
        var pane = new Pane();
        pane.setLayoutX(i.x); pane.setLayoutY(i.y);
        pane.setPrefWidth(i.width); pane.setPrefHeight(i.height);
        setBg(pane, i.bgColor, i.bgImage, i.width, i.height);

        instanceMap.put(i.instanceID, new InstanceView(i, pane));

        return pane;
    }

    public static InstanceView getInstanceById(String id) {
        return instanceMap.get(id);
    }
}
