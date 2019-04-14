package Engine.src.Components;

import java.util.List;

public class StateComponent extends Component {
    private List<String> myPossibleStates;
    private String myState;

    public StateComponent(List<String> states, String state) {
        myPossibleStates = states;
        myState = state;
    }
}
