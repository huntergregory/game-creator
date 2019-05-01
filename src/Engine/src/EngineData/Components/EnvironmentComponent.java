package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class EnvironmentComponent extends Component{

    private double myAccelX;
    private double myAccelY;
    private double myMaxXVelocity;
    private double myMaxYVelocity;
    private double myDragCoeff;

    public EnvironmentComponent(String accelX, String accelY, String dragCoeff, String maxXVel, String maxYVel) {
        myAccelX = parseDouble(accelX);
        myAccelX = parseDouble(accelY);
        myDragCoeff = parseDouble(dragCoeff);
        myMaxXVelocity = parseDouble(maxXVel);
        myMaxYVelocity = parseDouble(maxYVel);
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
