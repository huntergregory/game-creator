<<<<<<< HEAD:src/Engine/src/EngineData/Components/CheckpointComponent.java
package Engine.src.EngineData.Components;
=======
package Engine.src.Components;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/CheckpointComponent.java

public class CheckpointComponent extends Component{
    double myX;
    double myY;

<<<<<<< HEAD:src/Engine/src/EngineData/Components/CheckpointComponent.java
    public CheckpointComponent(String x, String y){
        myX = Double.parseDouble(x);
        myY = Double.parseDouble(y);
=======
    public CheckpointComponent(int x, int y){
        myX = x;
        myY = y;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/CheckpointComponent.java
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
