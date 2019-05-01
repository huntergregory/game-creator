package Player;

/**
 *  Tracks the progress through entire game.
 */
public interface GameProgress {
    /**
     * The user's current score in the current game.
     * @return an integer representing the current score
     */
    int getScore();

    /**
     * The user's score is changed at the end of the level/once the checkpoint is reached.
     */
    void updateScore();

    /**
     * Interacts with LevelProgress to load a new level. GameProgress keeps track of current level.
     *
     * @param level number of next level
     * @return a Level object
     */
    Level loadLevel(int level);
}