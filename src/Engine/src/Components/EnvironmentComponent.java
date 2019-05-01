package Engine.src.Components;

public class EnvironmentComponent extends Component{
    private double myAccelX;
    private double myAccelY;
    private double myVelDampener;
    private double myFriction;

    public EnvironmentComponent(double accelX, double accelY, double velDampener, double friction) {
        myAccelX = accelX;
        myAccelX = accelY;
        myVelDampener = velDampener;
        myFriction = friction;
    }

    public double getAccelX() {
        return myAccelX;
    }

    public double getAccelY() {
        return myAccelY;
    }

    public double getVelDamper() {
        return myVelDampener;
    }

    public double getFriction() {
        return myFriction;
    }
}
