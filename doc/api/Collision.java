package Engine;

public interface Collision {
    /**
     * The method that takes the EngineGameObject pair and it's response and performs it on each of the GameObjects, updating
     * the necessary information of each object
     * @param pair one of the defined EngineGameObject pairs determined by the current gameObjects colliding, taken from a map of
     *             the possible set of EngineGameObject pairs created at the start of the game from the data
     * @param response the interaction necessary between the gameObjects as a result of the collision, which can possibly be
     *                 defined by the user
     */
    void collide(GameObjectPair pair, Response response);
}