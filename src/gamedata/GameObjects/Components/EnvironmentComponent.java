package gamedata.GameObjects.Components;

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

    public double getUpdatedAccelX(double velocity) {
        double dragSign = -1 * velocity / Math.abs(velocity);
        double dragAccel = myDragCoeff * Math.pow(velocity, 2);
        return myAccelX + dragSign * dragAccel;
    }

    public double getUpdatedAccelY(double velocity) {
        double dragSign = -1 * velocity / Math.abs(velocity);
        double dragAccel = myDragCoeff * Math.pow(velocity, 2);
        return myAccelY + dragSign * dragAccel;
    }

    public double getMaxXVelocity() {
        return myMaxXVelocity;
    }

    public double getMaxYVelocity() {
        return myMaxYVelocity;
    }
}
