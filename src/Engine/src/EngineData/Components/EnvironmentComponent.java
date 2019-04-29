package Engine.src.EngineData.Components;

public class EnvironmentComponent extends Component{

    private double myAccelX;
    private double myAccelY;
    private double myMaxXVelocity;
    private double myMaxYVelocity;
    private double myDragCoeff;

    public EnvironmentComponent(double accelX, double accelY, double dragCoeff, double maxXVel, double maxYVel) {
        myAccelX = accelX;
        myAccelX = accelY;
        myDragCoeff = dragCoeff;
        myMaxXVelocity = maxXVel;
        myMaxYVelocity = maxYVel;
    }
    //Using k*v instead of k*v^2
    public double getUpdatedAccel(double velocity) {
        double dragSign = -1 * velocity / Math.abs(velocity);
        double dragAccel = myDragCoeff * velocity / 2;
        return myAccelX + dragSign * dragAccel;
    }

    public double getUpdatedMovementVelocity(double velocity, double defaultVel) {
        return velocity - myDragCoeff * defaultVel;
    }

    public double getMaxXVelocity() {
        return myMaxXVelocity;
    }

    public double getMaxYVelocity() {
        return myMaxYVelocity;
    }
}
