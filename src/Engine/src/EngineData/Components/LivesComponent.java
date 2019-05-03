
package Engine.src.EngineData.Components;

import static java.lang.Integer.parseInt;

public class LivesComponent extends Component{
    private int myLives;
    private String myRespawnInstructions;

    public LivesComponent(String lives, String respawnInstructions){
        myLives = parseInt(lives);
        myRespawnInstructions = respawnInstructions;
    }

    public void addLife(){
        myLives++;
    }

    public void removeLife(){
        myLives--;
    }

    public boolean expired(){
        return myLives == 0;
    }

    public String getRespawnInstructions(){
        return myRespawnInstructions;
    }

    @Override
    public Component copy() {
        return new LivesComponent(Integer.toString(myLives), myRespawnInstructions);
    }

}
