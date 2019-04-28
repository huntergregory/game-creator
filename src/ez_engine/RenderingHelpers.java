package ez_engine;

import javafx.scene.Group;
import javafx.scene.Scene;
import static auth.Dimensions.*;

public class RenderingHelpers {
    public static Scene renderScene(gamedata.Scene scene) {
        var parentGroup = new Group();
        Scene toReturn = new Scene(parentGroup);

        return toReturn;
    }
}
