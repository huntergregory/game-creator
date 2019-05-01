package Engine;

public interface Collision {
    /**
     * The method that takes the GameObject pair and it's response and performs it on each of the GameObjects, updating
     * the necessary information of each object
     * @param pair one of the defined GameObject pairs determined by the current gameObjects colliding, taken from a map of
     *             the possible set of GameObject pairs created at the start of the game from the data
     * @param response the interaction necessary between the gameObjects as a result of the collision, which can possibly be
     *                 defined by the user
     */
    void collide(GameObjectPair pair, Response response);
}