package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class JumpComponent extends Component {
    public double myJumpVelocity;

    /**
     * Input a positive value to jump up
     * @param jumpVelocity
     */
    public JumpComponent(String jumpVelocity) {
        myJumpVelocity = -parseDouble(jumpVelocity);
    }

    public double getJumpVelocity() {
        return myJumpVelocity;
    }

    @Override
    public Component copy() {
        return new JumpComponent(Double.toString(-myJumpVelocity));
    }

}
