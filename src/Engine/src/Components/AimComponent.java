package Engine.src.Components;

public class AimComponent extends Component{

    private double myXAim;
    private double myYAim;
    private double myRotationRate;
    private int myShootRate;
    private int myTracker;

<<<<<<< HEAD:src/Engine/src/EngineData/Components/AimComponent.java
    public AimComponent(String xAim, String yAim, String rotationRate, String shootRate){
        myXAim = parseDouble(xAim);
        myYAim = parseDouble(yAim);
        myRotationRate = Double.parseDouble(rotationRate);
        myShootRate = Integer.parseInt(shootRate);
        myTracker = 0;
=======
    public AimComponent( double xAim, double yAim, double rotationRate){
        myXAim = xAim;
        myYAim = yAim;
        myRotationRate = rotationRate;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/AimComponent.java
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
