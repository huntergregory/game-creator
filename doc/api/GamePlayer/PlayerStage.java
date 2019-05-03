package Player.Regions;

/**
 * Interface for PlayerStage, the GUI provided for the user to select which game to play. The PlayerStage should be
 * closed and only interacts with other Components in Player, i.e. is internal. PlayerStage should load several regions
 * that each define a game or an action for the user.
 */
public interface PlayerStage {
    /**
     * The Starter class can call PlayerStageInstance.makeStage() to get the Stage, which is the view.
     * The implementation for creating the scene in the stage should be done in a separate class, makeStage just
     * returns the stage, it does not create the scene.
     *
     * @return a Stage containing the main GUI
     */
    Stage makeStage();
}