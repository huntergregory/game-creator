package Engine.src.Components;

public class CheckpointComponent extends Component{
    int myX;
    int myY;

    public CheckpointComponent(int x, int y){
        myX = x;
        myY = y;
    }

    public void setCheckpoint(int x, int y){
        myX = x;
        myY = y;
    }

    public int getX(){
        return myX;
    }

    public int getY(){
        return myY;
    }

}
