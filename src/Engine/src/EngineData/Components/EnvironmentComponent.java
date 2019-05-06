package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * Component that keeps of whether the EngineInstance is an environment
 * @author David Liu
 */
public class EnvironmentComponent extends Component{
    private double myAccelX;
    private double myAccelY;
    private double myMaxXVelocity;
    private double myMaxYVelocity;
    private double myDragCoeff;

    /**
     * Initializes the conditions if an EngineInstance is an environment
     * @param accelX x acceleration
     * @param accelY y acceleration
     * @param dragCoeff drag coefficient
     * @param maxXVel maximum x velocity
     * @param maxYVel maximum y velocity
     */
    public EnvironmentComponent(String accelX, String accelY, String dragCoeff, String maxXVel, String maxYVel) {
        myAccelX = parseDouble(accelX);
        myAccelY = parseDouble(accelY);
        myDragCoeff = parseDouble(dragCoeff);
        myMaxXVelocity = parseDouble(maxXVel);
        myMaxYVelocity = parseDouble(maxYVel);
    }

    /**
     * Formula for other engine instances to calculate their acceleration in this environment
     * @param velocity velocity of another EngineInstance
     * @return updated acceleration for a velocity that interacts in this environment
     */
    public double getUpdatedAccel(double velocity) {
        double dragSign = -1 * velocity / Math.abs(velocity);
        double dragAccel = myDragCoeff * velocity / 2;
        return myAccelX + dragSign * dragAccel;
    }

    /**
     * Formula for other engine instances to calculate their movement velocity in this environment
     * @param velocity velocity of another EngineInstance
     * @param defaultVel default velocity of another EngineInstance
     * @return updated movement velocity for a velocity and default velocity that interacts in this environment
     */
    public double getUpdatedMovementVelocity(double velocity, double defaultVel) {
        return velocity - myDragCoeff * defaultVel;
    }

    /**
     * Gives maximum x velocity
     * @return maximum x velocity
     */
    public double getMaxXVelocity() {
        return myMaxXVelocity;
    }

    /**
     * Gives maximum y velocity
     * @return maximum y velocity
     */
    public double getMaxYVelocity() {
        return myMaxYVelocity;
    }

    /**
     * Gives a copy of the EnvironmentComponent
     * @return copy of the EnvironmentComponent
     */
    @Override
    public Component copy() {
        return new EnvironmentComponent(Double.toString(myAccelX), Double.toString(myAccelY), Double.toString(myDragCoeff), Double.toString(myMaxXVelocity), Double.toString(myMaxYVelocity));
    }

}
