package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class AimComponent extends Component{

    double myXAim;
    double myYAim;
    double myRotationRate;
    double myShootRate;
    double myTracker;

    public AimComponent(String xAim, String yAim, String rotationRate, String shootRate){
        myXAim = parseDouble(xAim);
        myYAim = parseDouble(yAim);
        myRotationRate = Integer.parseInt(rotationRate);
        myShootRate = Integer.parseInt(shootRate);
        myTracker = 0;
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

    public void updateTracker(){
        myTracker++;
    }

    public double getMyTracker() {
        return myTracker;
    }

    public double getMyShootRate() {
        return myShootRate;
    }
}
