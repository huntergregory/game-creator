package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class AimComponent extends Component{

    private double myXAim;
    private double myYAim;
    private double myRotationRate;
    private int myShootRate;
    private int myTracker;

    public AimComponent(String xAim, String yAim, String rotationRate, String shootRate){
        myXAim = parseDouble(xAim);
        myYAim = parseDouble(yAim);
        myRotationRate = Double.parseDouble(rotationRate);
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
        myYAim = yAim;
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

    public void resetTracker(){
        myTracker = 0;
    }

    @Override
    public Component copy() {
        return new AimComponent(Double.toString(myXAim), Double.toString(myYAim), Double.toString(myRotationRate), Integer.toString(myShootRate));
    }

}
