package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * Assumes time step is 1 (multiplies velocity by 1 when returning new position or acceleration by 1 when updating velocity)
 */
public class MotionComponent extends Component {
    private final double MY_DEFAULT_X_ACCEL;
    private final double MY_DEFAULT_Y_ACCEL;
    private final double MY_DEFAULT_MOVEMENT_X_VEL;
    private final double MY_DEFAULT_MOVEMENT_Y_VEL;
    private double myXVelocity;
    private double myYVelocity;
    private double myMovementXVelocity;
    private double myMovementYVelocity;
    private double myXAccel;
    private double myYAccel;

    public MotionComponent(String xVelocity, String yVelocity, String defaultMovementXVelocity, String defaultMovementYVelocity, String defaultAccelX, String defaultAccelY) {
        this.myXVelocity = parseDouble(xVelocity);
        this.myYVelocity = parseDouble(yVelocity);
        this.myMovementXVelocity = parseDouble(defaultMovementXVelocity);
        this.myMovementYVelocity = parseDouble(defaultMovementYVelocity);
        this.MY_DEFAULT_MOVEMENT_X_VEL = parseDouble(defaultMovementXVelocity);
        this.MY_DEFAULT_MOVEMENT_Y_VEL = parseDouble(defaultMovementYVelocity);
        this.myXAccel = parseDouble(defaultAccelX);
        this.myYAccel = parseDouble(defaultAccelY);
        this.MY_DEFAULT_X_ACCEL = parseDouble(defaultAccelX);
        this.MY_DEFAULT_Y_ACCEL = parseDouble(defaultAccelY);
    }

    public double getMovementXVelocity() {
        return myMovementXVelocity;
    }

    public double getMovementYVelocity() {
        return myMovementYVelocity;
    }

    public double getMovementVelocity(){
        return Math.sqrt(Math.pow(myMovementXVelocity, 2) + Math.pow(myMovementYVelocity, 2));
    }

    public void setMovementXVelocity(double movementXVelocity) {
        myMovementXVelocity = movementXVelocity;
    }

    public void setMovementYVelocity(double movementYVelocity) {
        myMovementYVelocity = movementYVelocity;
    }

    public double getVelocity(){
        return Math.sqrt(Math.pow(myXVelocity, 2) + Math.pow(myYVelocity, 2));
    }

    public double getXVelocity() {
        return myXVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.myXVelocity = xVelocity;
    }

    public double getYVelocity() {
        return myYVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.myYVelocity = yVelocity;
    }

    public double getXAccel() { return myXAccel; }

    public void setXAccel(double xAccel) { myXAccel = xAccel; }

    public double getYAccel() { return myYAccel; }

    public void setYAccel(double yAccel) { myYAccel = yAccel; }

    public double getDefaultXAccel() { return MY_DEFAULT_X_ACCEL; }

    public double getDefaultYAccel() { return MY_DEFAULT_Y_ACCEL; }

    public void resetXAccel() { myXAccel = MY_DEFAULT_X_ACCEL; }

    public void resetYAccel() { myYAccel = MY_DEFAULT_Y_ACCEL; }

    public double getDefaultMovementXVel() { return MY_DEFAULT_MOVEMENT_X_VEL; }

    public double getDefaultMovementYVel() { return MY_DEFAULT_MOVEMENT_Y_VEL; }

    public void resetMovementXVel() { myMovementXVelocity = MY_DEFAULT_MOVEMENT_X_VEL; }

    public void resetMovementYVel() { myMovementYVelocity = MY_DEFAULT_MOVEMENT_Y_VEL; }
}
