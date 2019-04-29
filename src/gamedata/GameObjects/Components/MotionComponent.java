package gamedata.GameObjects.Components;

/**
 * Assumes time step is 1 (multiplies velocity by 1 when returning new position or acceleration by 1 when updating velocity)
 */
public class MotionComponent extends Component {
    private double myXVelocity;
    private double myYVelocity;
    private double myMovementXVelocity;
    private double myMovementYVelocity;
    private EnvironmentComponent myEnvironmentImmersedIn;

    public MotionComponent(double xVelocity, double yVelocity, double movementXVelocity, double movementYVelocity, EnvironmentComponent currentEnvironment) {
        this.myXVelocity = xVelocity;
        this.myYVelocity = yVelocity;
        this.myMovementXVelocity = movementXVelocity;
        this.myMovementYVelocity = movementYVelocity;
        myEnvironmentImmersedIn = currentEnvironment;
    }

    public EnvironmentComponent getEnvironmentImmersedIn() {
        return myEnvironmentImmersedIn;
    }

    public void setEnvironmentImmersedIn(EnvironmentComponent environmentComponent) {
        myEnvironmentImmersedIn = environmentComponent;
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
}
