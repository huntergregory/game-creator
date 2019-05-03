package Engine;

public interface GameObject {
    /**
     * Executes the logic determined by the json files on each GameObject as necessary excluding collisions, which can
     * include basic player movement parameters, special effects, etc.
     */
    void execute();
    /**
     * Gets the absolute x position of the GameObject
     * @return absolute x position of the GameObject
     */
    double getXPosition();
    /**
     * Gets the absolute y position of the GameObject
     * @return absolute y position of the GameObject
     */
    double getYPosition();
    /**
     * Gets the magnitude of the velocity of the GameObject
     * @return magnitude of the velocity
     */
    double getVel();
    /**
     * Moves the object a set amount based on the speed of the object and our frame rate
     */
    void move();
}