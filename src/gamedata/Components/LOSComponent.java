package Engine.src.Components;

public class LOSComponent extends Component{

    double myLOS;

    public LOSComponent(double LOS){
        myLOS = LOS;
    }

    public double getLOS(){
        return myLOS;
    }
}
