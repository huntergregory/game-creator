package GameObjects;

import GameObjects.GameObject;
/**
 * @author Jonathan Yu
 */
public class Character extends GameObject {

    private double myJumpSpeed;

    public Character(double xPos, double yPos, double health, double height, double width,
                double angle, double velocity, String objectName, int zIndex, double[] direction, String filename, double jumpSpeed) {
        super(xPos, yPos, health, height, width, angle, velocity, objectName, zIndex, direction, filename);
        myJumpSpeed = jumpSpeed;
    }

    public void jump() {
        double[] nextDirection = getDirection();

        nextDirection[1] = nextDirection[1] + myJumpSpeed;
        setDirection(nextDirection);
    }
}
