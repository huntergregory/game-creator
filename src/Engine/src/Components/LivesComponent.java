package Engine.src.Components;

public class LivesComponent extends Component{
    private int myLives;
    private String myRespawnInstructions;

    public LivesComponent(int lives, String respawnInstructions){
        myLives = lives;
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
}
