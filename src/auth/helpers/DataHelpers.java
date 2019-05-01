package auth.helpers;

import auth.screens.CanvasScreen;
import gamedata.Game;
import gamedata.GameObject;
import gamedata.Resource;
import gamedata.Scene;

public class DataHelpers {
    public static Scene createNewScene(int number) {
        var scene = new Scene();
        scene.sceneID = "scene_"+number;
        return scene;
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

}
