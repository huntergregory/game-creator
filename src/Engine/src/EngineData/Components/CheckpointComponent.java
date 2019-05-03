package Engine.src.EngineData.Components;

public class CheckpointComponent extends Component{
    double myX;
    double myY;

    public CheckpointComponent(String x, String y){
        myX = Double.parseDouble(x);
        myY = Double.parseDouble(y);
    }

    public void setCheckpoint(int x, int y){
        myX = x;
        myY = y;
    }

    public double getX(){
        return myX;
    }

    public double getY(){
        return myY;
    }

    @Override
    public Component copy() {
        return new CheckpointComponent(Double.toString(myX), Double.toString(myY));
    }
}
