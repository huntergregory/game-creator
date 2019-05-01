package Player;

/**
 *  Used to track user's progress through a single level of the game.
 */
public interface LevelProgress {
    /**
     *  Loads level into the game.
     */
    void loadLevel();

    /**
     *  Saves progress in current level.
     */
    void saveLevel();

    /**
     *  Pauses the current level. Occurs in conjunction with menu.
     */
    void pause();

    /**
     *  Resumes the current level.
     */
    void resume();

    /**
     *  Win condition(s) for current level is reached. Control is transferred back to Controller.
     */
    void win();

    /**
     *  Lose condition(s) for current level is reached. Control is transferred back to Controller.
     */
    void lose();

    /**
     *  Next level is queried.
     */
    void updateLevel();
}