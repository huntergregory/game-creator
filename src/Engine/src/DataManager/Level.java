package Engine.src.DataManager;

import Engine.src.Components.Component;
import Engine.src.Triggers.Events.Event;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public class Level {
    // TODO: Establish what must be passed with the new entity based backend
    private Map<Integer, Map<String, Component>> myActiveObjects;
    private Map<String[], Set<Event>[]> myCollisionResponses;
    private List<String> myTypes;
    private Integer myLevelNumber;
    private List<Map<String, Component>> myObjectBank;
    private String myBackground;
    private Integer myWidth;
    private Integer myHeight;

    public Level(Integer levelNumber, Map<Integer, Map<String, Component>> activeObjects, List<Map<String, Component>> objectBank,
                 Map<String[], Set<Event>[]> collisionResponses, String background, int width, int height){
        myLevelNumber = levelNumber;
        myActiveObjects = activeObjects;
        myObjectBank = objectBank;
        myCollisionResponses = collisionResponses;
        myBackground = background;
        myWidth = width;
        myHeight = height;
    }

    public int getLevelNumber(){
        return myLevelNumber;
    }

    public Map<Integer, Map<String, Component>> getActiveObjects(){
        return Collections.unmodifiableMap(myActiveObjects);
    }

    public List<Map<String, Component>> getObjectBank(){
        return Collections.unmodifiableList(myObjectBank);
    }

    public Map<String[], Set<Event>[]> getCollisionResponses(){
        return Collections.unmodifiableMap(myCollisionResponses);
    }

    public String getBackground(){
        return myBackground;
    }
}
