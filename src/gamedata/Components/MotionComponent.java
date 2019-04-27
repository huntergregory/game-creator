package Engine.src.Components;

/**
 * Assumes time step is 1 (multiplies velocity by 1 when returning new position or acceleration by 1 when updating velocity)
 */
public class MotionComponent extends Component {
    private static final double MAX_X_VELOCITY = 20;
    private static final double MAX_Y_VELOCITY = 20;

    private double myXVelocity;
    private double myYVelocity;
    private double myXAcceleration;
    private double myYAcceleration;
    private double myAngle;
    private double myMaxXVelocity = MAX_X_VELOCITY;
    private double myMaxYVelocity = MAX_Y_VELOCITY;
    private double myMovementXVelocity;
    private double myMovementYVelocity;

    public MotionComponent(double xVelocity, double yVelocity, double xAcceleration, double yAcceleration, double angle,
                           double movementXVelocity, double movementYVelocity) {
        this.myXVelocity = xVelocity;
        this.myYVelocity = yVelocity;
        this.myXAcceleration = xAcceleration;
        this.myYAcceleration = yAcceleration;
        this.myAngle = angle;
        this.myMovementXVelocity = movementXVelocity;
        this.myMovementYVelocity = movementYVelocity;
    }

    public double getMovementXVelocity() {
        return myMovementXVelocity;
    }

    public double getMovementYVelocity() {
        return myMovementYVelocity;
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

    public double getXAcceleration() {
        return myXAcceleration;
    }

    public void setXAcceleration(double xAcceleration) {
        this.myXAcceleration = xAcceleration;
    }

    public double getYAcceleration() {
        return myYAcceleration;
    }

    public void setYAcceleration(double yAcceleration) {
        this.myYAcceleration = yAcceleration;
    }

    public double getAngle() {
        return myAngle;
    }

    public double getVelocity(){
        return Math.pow(Math.pow(myXVelocity, 2) + Math.pow(myYVelocity, 2), .5);
    }

    public double getMovementVelocity(){
        return Math.pow(Math.pow(myMovementXVelocity, 2) + Math.pow(myMovementYVelocity, 2), .5);
    }

    //Should put the three methods below into entitymanager?
    public void updateVelocity() {
        myXVelocity = Math.min(myXVelocity += myXAcceleration, myMaxXVelocity);
        myYVelocity = Math.min(myYVelocity += myYAcceleration, myMaxYVelocity);
    }

    public double getNewX(double x) {
        return x + myXVelocity;
    }

    public double getNewY(double y) {
        return y + myYVelocity;
    }

    public void adjustDirection(double delta) {
        myAngle += delta;
        adjustVelocitiesByAngle(myAngle);
    }

    public void setDirection(double angle) {
        myAngle = angle;
        adjustVelocitiesByAngle(myAngle);
    }

    private void adjustVelocitiesByAngle(double angle) {
        double[] directionVec = calculateDirection(myAngle);
        double totalVel = myXVelocity*myXVelocity + myYVelocity*myYVelocity;
        myXVelocity = totalVel * directionVec[0];
        myYVelocity = totalVel * directionVec[1];
    }

    private double[] calculateDirection(double angle){
        double[] directionVec = new double[2];
        directionVec[0] = Math.cos(Math.toRadians(angle));
        directionVec[1] = Math.sin(Math.toRadians(angle));
        return directionVec;
    }
}
