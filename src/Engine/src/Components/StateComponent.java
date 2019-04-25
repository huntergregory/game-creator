package Engine.src.Components;

import java.util.List;

public class StateComponent extends Component {
    private List<String> myStates;

    public StateComponent(List<String> states) {
        myStates = states;
    }

    public boolean hasState(String state){
        return myStates.contains(state);
    }

    public void addState(String state){
        myStates.add(state);
    }

    public void removeState(String state){
        myStates.remove(state);
    }

}
