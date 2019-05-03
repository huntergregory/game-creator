package auth.helpers;

import auth.screens.CanvasScreen;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gamedata.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DataHelpers {
    public static String SERVICE_ACCOUNT_JSON_PATH = "/Users/anshudwibhashi/work/school/CS308/voogasalad_crackingopen/lib/TMTP-b2dc645337e7.json";
    public static Scene createNewScene(int number) {
        var scene = new Scene();
        scene.sceneID = "scene_"+number;
//        scene.sceneLogic = "// Type your Groovy scripts for " + scene.sceneID + " here";
        return scene;
    }

    public static void exportGame(String img, String name, String desc, String src) {
        try {
            JSONObject obj = new JSONObject(new Scanner(new File("data/player_data.json")).useDelimiter("\\Z").next());
            var games = obj.getJSONArray("games");
            JSONObject newGame = new JSONObject();
            newGame.put("game_source", src);
            newGame.put("desc", desc);
            newGame.put("img", img);
            newGame.put("name", name);
            newGame.put("favorite", "false");
            newGame.put("rating", "0.0");
            games.put(newGame);

            FileWriter writer = new FileWriter("data/player_data.json");
            writer.write(obj.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean sceneIDExists(Game game, String sceneID) {
        for (var s : game.scenes) {
            if (s.sceneID.equals(sceneID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean colorExists(Game game, String id) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(Resource.ResourceType.COLOR_RESOURCE)) {
                return true;
            }
        }
        return false;
    }

    public static boolean imgExists(Game game, String id) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(Resource.ResourceType.IMAGE_RESOURCE)) {
                return true;
            }
        }
        return false;
    }

    public static Resource getResourceByType(Game game, String id, Resource.ResourceType type) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(type)) {
                return s;
            }
        }
        return null;
    }

    public static boolean resourceIDExists(Game game, String id, Resource.ResourceType type) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static GameObject getObjectByID(Game game, String id) {
        for (var s : game.gameObjects) {
            if (s.objectID.equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static Instance getInstanceByID(Game game, String id, int currentScene) {
        for (var s : game.scenes.get(currentScene).instances) {
            if (s.instanceID.equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static boolean objectIDExists(Game game, String id) {
        for (var s : game.gameObjects) {
            if (s.objectID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static void updateObjectIDReferences(CanvasScreen context, Game game, String oldID, String newID) {
        context.selectedID = newID;
        for (var s : game.scenes) {
            for (var i : s.instances) {
                if (i.instanceOf.equals(oldID)) {
                    i.instanceOf = newID;
                }
            }
        }
    }

    public static void updateResourceIDReferences(CanvasScreen context, Game game, String oldID, String newID, Resource.ResourceType type) {
        context.selectedID = newID;
        for (var s : game.scenes) {
            if (type.equals(Resource.ResourceType.IMAGE_RESOURCE) && s.bgImage.equals(oldID)) {
                s.bgImage = newID;
            }
            if (type.equals(Resource.ResourceType.COLOR_RESOURCE) && s.bgColor.equals(oldID)) {
                s.bgColor = newID;
            }
            for (var i : s.instances) {
                if (type.equals(Resource.ResourceType.IMAGE_RESOURCE) && i.bgImage.equals(oldID)) {
                    i.bgImage = newID;
                }
                if (type.equals(Resource.ResourceType.COLOR_RESOURCE) && i.bgColor.equals(oldID)) {
                    i.bgColor = newID;
                }
            }
        }
        for (var o : game.gameObjects) {
            if (type.equals(Resource.ResourceType.IMAGE_RESOURCE) && o.bgImage.equals(oldID)) {
                o.bgImage = newID;
            }
            if (type.equals(Resource.ResourceType.COLOR_RESOURCE) && o.bgColor.equals(oldID)) {
                o.bgColor = newID;
            }
        }
    }

    public static void sendNewCloudData(CanvasScreen context) {
        try {
            GameCloudWrapper gcw = context.getGameCloudWrapper();
            context.triggerEvent(gcw.gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
