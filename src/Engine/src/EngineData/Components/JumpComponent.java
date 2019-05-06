package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * Component that allows the EngineInstance ot jump
 * @author David Liu
 */
public class JumpComponent extends Component {
    public double myJumpVelocity;

    /**
     * Input a positive value to jump up
     * @param jumpVelocity jump Velocity
     */
    public JumpComponent(String jumpVelocity) {
        myJumpVelocity = -parseDouble(jumpVelocity);
    }

    /**
     * Gives jump velocity
     * @return jump velocity
     */
    public double getJumpVelocity() {
        return myJumpVelocity;
    }

    /**
     * Gives a copy of the JumpComponent
     * @return copy of the JumpComponent
     */
    @Override
    public Component copy() {
        return new JumpComponent(Double.toString(-myJumpVelocity));
    }

}
