package Engine.src.EngineData.Components;

import static java.lang.Integer.parseInt;

public class CheckpointComponent extends Component{
    int myX;
    int myY;

    public CheckpointComponent(String x, String y){
        myX = parseInt(x);
        myY = parseInt(y);
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
