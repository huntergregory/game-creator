
package Engine.src.EngineData.Components;

import static java.lang.Integer.parseInt;

/**
 * Component that keeps track of the lives of an EngineInstance
 * @author David Liu
 */
public class LivesComponent extends Component{
    private int myLives;
    private String myRespawnInstructions;

    /**
     * Assumes lives can be parsed as an int
     * @param lives number of lives
     * @param respawnInstructions instructions on what to do when the EngineInstance dies
     */
    public LivesComponent(String lives, String respawnInstructions){
        myLives = parseInt(lives);
        myRespawnInstructions = respawnInstructions;
    }

    /**
     * Gives number of lives
     * @return number of lives
     */
    public int getLives() {
        return myLives;
    }

    /**
     * Sets number of lives
     * @param lives new number of lives
     */
    public void setLives(int lives){
        myLives = lives;
    }

    /**
     * Removes a life
     */
    public void removeLife(){
        myLives--;
    }

    /**
     * Gives condition of whether EngineInstance should be removed forever
     * @return whether the EngineInstance should be removed completely forever (no respawn)
     */
    public boolean expired(){
        return myLives == 0;
    }

    /**
     * Gives respawn instructions
     * @return respawn instructions
     */
    public String getRespawnInstructions(){
        return myRespawnInstructions;
    }

    /**
     * Gives a copy of the LivesComponent
     * @return copy of the LivesComponent
     */
    @Override
    public Component copy() {
        return new LivesComponent(Integer.toString(myLives), myRespawnInstructions);
    }

}
