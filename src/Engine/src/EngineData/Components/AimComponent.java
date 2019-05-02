package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class AimComponent extends Component{

    double myXAim;
    double myYAim;
    double myRotationRate;

    public AimComponent( String xAim, String yAim, String rotationRate){
        myXAim = parseDouble(xAim);
        myYAim = parseDouble(yAim);
        myRotationRate = parseDouble(rotationRate);
    }

    public double getXAim(){
        return myXAim;
    }

    public double getYAim(){
        return myYAim;
    }

    public double getRotationRate(){
        return myRotationRate;
    }

    public void setXAim(double xAim){
        myXAim = xAim;
    }

    public void setYAim(double yAim){
        myXAim = yAim;
    }

}
