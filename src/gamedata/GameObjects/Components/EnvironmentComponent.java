package gamedata.GameObjects.Components;

public class EnvironmentComponent extends Component{

    private static final double MAX_X_VELOCITY = 20;
    private static final double MAX_Y_VELOCITY = 20; //FIXME defaults


    private double myAccelX;
    private double myAccelY;
    private double myMaxXVelocity = MAX_X_VELOCITY;
    private double myMaxYVelocity = MAX_Y_VELOCITY;
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

    public double getMaxXVelocity() {
        return myMaxXVelocity;
    }

    public double getMaxYVelocity() {
        return myMaxYVelocity;
    }

    public void setMaxXVelocity(double max) {
        if (max < 0)
            max = 0;
        myMaxXVelocity = max;
    }

    public void setMaxYVelocity(double max) {
        if (max < 0)
            max = 0;
        myMaxYVelocity = max;
    }
}
