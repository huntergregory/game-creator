package Engine.src.Components;

public class AimComponent extends Component{

    double myXAim;
    double myYAim;
    double myRotationRate;

    public AimComponent( double xAim, double yAim, double rotationRate){
        myXAim = xAim;
        myYAim = yAim;
        myRotationRate = rotationRate;
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

    public void setXYAim(double yAim){
        myXAim = yAim;
    }

}
