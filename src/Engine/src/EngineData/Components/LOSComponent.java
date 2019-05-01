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
}
