package gamedata.GameObjects.Components;

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

    public MotionComponent(double xVelocity, double yVelocity, double defaultMovementXVelocity, double defaultMovementYVelocity, double defaultAccelX, double defaultAccelY) {
        this.myXVelocity = xVelocity;
        this.myYVelocity = yVelocity;
        this.myMovementXVelocity = defaultMovementXVelocity;
        this.myMovementYVelocity = defaultMovementYVelocity;
        this.MY_DEFAULT_MOVEMENT_X_VEL = defaultMovementXVelocity;
        this.MY_DEFAULT_MOVEMENT_Y_VEL = defaultMovementYVelocity;
        this.myXAccel = defaultAccelX;
        this.myYAccel = defaultAccelY;
        this.MY_DEFAULT_X_ACCEL = defaultAccelX;
        this.MY_DEFAULT_Y_ACCEL = defaultAccelY;
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
