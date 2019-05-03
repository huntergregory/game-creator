package Engine.src.Components;

public class JumpComponent extends Component {
    public double myJumpVelocity;

    /**
     * Input a positive value to jump up
     * @param jumpVelocity
     */
    public JumpComponent(double jumpVelocity) {
        myJumpVelocity = -jumpVelocity;
    }

    public double getJumpVelocity() {
        return myJumpVelocity;
    }

    @Override
    public Component copy() {
        return new JumpComponent(Double.toString(-myJumpVelocity));
    }

}
