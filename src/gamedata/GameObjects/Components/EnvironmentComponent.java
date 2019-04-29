package gamedata.GameObjects.Components;

public class EnvironmentComponent extends Component{

    private double myAccelX;
    private double myAccelY;
    private double myMaxXVelocity;
    private double myMaxYVelocity;
    private double myVelDampener;
    private double myFriction;

    public EnvironmentComponent(double accelX, double accelY, double velDampener, double friction, double maxXVel, double maxYVel) {
        myAccelX = accelX;
        myAccelX = accelY;
        myVelDampener = velDampener;
        myFriction = friction;
        myMaxXVelocity = maxXVel;
        myMaxYVelocity = maxYVel;
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

    public double getMaxXVelocity() {
        return myMaxXVelocity;
    }

    public double getMaxYVelocity() {
        return myMaxYVelocity;
    }
}
