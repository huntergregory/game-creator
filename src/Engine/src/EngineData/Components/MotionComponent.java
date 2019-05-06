package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * Component that keeps track of the motion of an EngineInstance
 * @author David Liu
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

    /**
     * Assumes the initial movement velocities and accelerations are the defaults
     * @param xVelocity x velocity
     * @param yVelocity y velocity
     * @param defaultMovementXVelocity x movement velocity
     * @param defaultMovementYVelocity y movement velocity
     * @param defaultAccelX x acceleration
     * @param defaultAccelY y acceleration
     */
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

    /**
     * Gives x movement velocity
     * @return current x movement velocity
     */
    public double getMovementXVelocity() {
        return myMovementXVelocity;
    }

    /**
     * Gives y movement velocity
     * @return current y movement velocity
     */
    public double getMovementYVelocity() {
        return myMovementYVelocity;
    }

    /**
     * Gives overall movement velocity
     * @return movement velocity combined from x and y
     */
    public double getMovementVelocity(){
        return Math.sqrt(Math.pow(myMovementXVelocity, 2) + Math.pow(myMovementYVelocity, 2));
    }

    /**
     * Set x movement velocity
     * @param movementXVelocity new movement x velocity
     */
    public void setMovementXVelocity(double movementXVelocity) {
        myMovementXVelocity = movementXVelocity;
    }

    /**
     * Set y movement velocity
     * @param movementYVelocity new movement y velocity
     */
    public void setMovementYVelocity(double movementYVelocity) {
        myMovementYVelocity = movementYVelocity;
    }

    /**
     * Gives overall velocity
     * @return overall velocity
     */
    public double getVelocity(){
        return Math.sqrt(Math.pow(myXVelocity, 2) + Math.pow(myYVelocity, 2));
    }

    /**
     * Gives x velocity
     * @return current x velocity
     */
    public double getXVelocity() {
        return myXVelocity;
    }

    /**
     * Sets x velocity
     * @param xVelocity new x velocity
     */
    public void setXVelocity(double xVelocity) {
        this.myXVelocity = xVelocity;
    }

    /**
     * Gives y velocity
     * @return current y velocity
     */
    public double getYVelocity() {
        return myYVelocity;
    }

    /**
     * Sets y velocity
     * @param yVelocity new y velocity
     */
    public void setYVelocity(double yVelocity) {
        this.myYVelocity = yVelocity;
    }

    /**
     * Gives x acceleration
     * @return current x acceleration
     */
    public double getXAccel() { return myXAccel; }

    /**
     * Sets x acceleration
     * @param xAccel new x acceleration
     */
    public void setXAccel(double xAccel) { myXAccel = xAccel; }

    /**
     * Gives y acceleration
     * @return current y acceleration
     */
    public double getYAccel() { return myYAccel; }

    /**
     * Sets y acceleration
     * @param yAccel new y acceleration
     */
    public void setYAccel(double yAccel) { myYAccel = yAccel; }

    /**
     * Gives default x acceleration
     * @return default x acceleration
     */
    public double getDefaultXAccel() { return MY_DEFAULT_X_ACCEL; }

    /**
     * Gives default y acceleration
     * @return default y acceleration
     */
    public double getDefaultYAccel() { return MY_DEFAULT_Y_ACCEL; }

    /**
     * Resets x acceleration to default
     */
    public void resetXAccel() { myXAccel = MY_DEFAULT_X_ACCEL; }

    /**
     * Resets y acceleration to default
     */
    public void resetYAccel() { myYAccel = MY_DEFAULT_Y_ACCEL; }

    /**
     * Gives default x movement velocity
     * @return current default x movement velocity
     */
    public double getDefaultMovementXVel() { return MY_DEFAULT_MOVEMENT_X_VEL; }

    /**
     * Gives default y movement velocity
     * @return current default y movement velocity
     */
    public double getDefaultMovementYVel() { return MY_DEFAULT_MOVEMENT_Y_VEL; }

    /**
     * Resets x movement velocity to default
     */
    public void resetMovementXVel() { myMovementXVelocity = MY_DEFAULT_MOVEMENT_X_VEL; }

    /**
     * Resets y movement velocity to default
     */
    public void resetMovementYVel() { myMovementYVelocity = MY_DEFAULT_MOVEMENT_Y_VEL; }

    /**
     * Gives a copy of the MotionComponent
     * @return copy of the MotionComponent
     */
    @Override
    public Component copy() {
        return new MotionComponent(Double.toString(myXVelocity), Double.toString(myYVelocity), Double.toString(myMovementXVelocity), Double.toString(myMovementYVelocity), Double.toString(myXAccel), Double.toString(myYAccel));
    }

}
