package auth.helpers;

import auth.screens.CanvasScreen;
import gamedata.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * This class was used to store methods that involved managing and checking data.
 *
 * @author Anshu Dwibhashi
 */
public class DataHelpers {
    public static String SERVICE_ACCOUNT_JSON_PATH = "/Users/anshudwibhashi/work/school/CS308/voogasalad_crackingopen/lib/TMTP-b2dc645337e7.json";

    /**
     * Creates a new Scene class and then sets its number.
     *
     * @param number The new number of scenes.
     * @return A new scene with no instances or objects.
     */
    public static Scene createNewScene(int number) {
        var scene = new Scene();
        scene.sceneID = "scene_"+number;
        scene.sceneLogic = "// Type your Groovy scripts for " + scene.sceneID + " here";
        return scene;
    }

    /**
     * Exports the game.
     *
     * @param img The path for thumbnail of the game.
     * @param name The name of the game.
     * @param desc The description of the game.
     * @param src The path to where the game file is.
     */
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

    /**
     * Checks if a Scene is in the current Game
     *
     * @param game The current Game.
     * @param sceneID The ID of a Scene class used to identify it.
     * @return true if the Scene exists or false otherwise.
     */
    public static boolean sceneIDExists(Game game, String sceneID) {
        for (var s : game.scenes) {
            if (s.sceneID.equals(sceneID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a color Resource is in the current Game
     *
     * @param game The current Game.
     * @param sceneID The ID of a Resource class used to identify it.
     * @return true if the Resource exists or false otherwise.
     */
    public static boolean colorExists(Game game, String id) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(Resource.ResourceType.COLOR_RESOURCE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a image Resource is in the current Game
     *
     * @param game The current Game.
     * @param sceneID The ID of a Resource class used to identify it.
     * @return true if the Resource exists or false otherwise.
     */
    public static boolean imgExists(Game game, String id) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(Resource.ResourceType.IMAGE_RESOURCE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a Resource object by using its ID and making sure its the right type of Resource needed.
     *
     * @param game The current Game.
     * @param id The ID of the resource.
     * @param type The type of Resource needed.
     * @return The Resource object specified or null.
     */
    public static Resource getResourceByType(Game game, String id, Resource.ResourceType type) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(type)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Checks if a Resource objects of a certain type exists.
     *
     * @param game The current Game
     * @param id The ID of the Resource
     * @param type The type that the Resource needs to be.
     * @return true of the Resource exists or false otherwise.
     */
    public static boolean resourceIDExists(Game game, String id, Resource.ResourceType type) {
        for (var s : game.resources) {
            if (s.resourceID.equals(id) && s.resourceType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a GameObject by its objectID.
     *
     * @param game The current Game.
     * @param id The objectID of the needed GameObject.
     * @return The specified object or null.
     */
    public static GameObject getObjectByID(Game game, String id) {
        for (var s : game.gameObjects) {
            if (s.objectID.equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Finds and returns a game Instance Class on specified scene.
     *
     * @param game The current Game.
     * @param id The ID of the Instance.
     * @param currentScene The number of the Scene to look through.
     * @return The specified Instance or null.
     */
    public static Instance getInstanceByID(Game game, String id, int currentScene) {
        for (var s : game.scenes.get(currentScene).instances) {
            if (s.instanceID.equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Checks if an GameObject exists.
     *
     *
     * @param game The current Game.
     * @param id The ID of the needed GameObject.
     * @return true if its exists, false otherwise.
     */
    public static boolean objectIDExists(Game game, String id) {
        for (var s : game.gameObjects) {
            if (s.objectID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and changes the objectID of every Instance of a GameObject when the user changes that GameObject's ID.
     *
     * @param context The CanvasScreen that represents the authoring environment.
     * @param game The current Game.
     * @param oldID The old objectID, used to find the Instances.
     * @param newID New ID to replace the old ID's with.
     */
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

    /**
     * Changes the resource ID of a Resource then also changes the resource ID of every GameObject that uses the resource.
     *
     * @param context The current CanvasScreen
     * @param game The current Game
     * @param oldID The old resource ID, used to find the GameObjects and Resource
     * @param newID The new ID to replace the old ID
     * @param type The that the Resource should be
     */
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

    /**
     * Sends the game data to the cloud.
     *
     * @param context The current scene.
     */
    public static void sendNewCloudData(CanvasScreen context) {
        try {
            GameCloudWrapper gcw = context.getGameCloudWrapper();
            context.triggerEvent(gcw.gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
