package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class LOSComponent extends Component{

    double myLOS;

    public LOSComponent(String LOS){
        myLOS = parseDouble(LOS);
    }

    public double getLOS(){
        return myLOS;
    }

    @Override
    public Component copy() {
        return new LOSComponent(Double.toString(myLOS));
    }

}
