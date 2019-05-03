package Engine.src.EngineData.Components;

import java.util.*;

public class StateComponent extends Component {
    private List<String> myStates;

    public StateComponent(String ... states) {
        myStates = new ArrayList<>();
        myStates.addAll(Arrays.asList(states));
    }

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

    @Override
    public Component copy() {
        return new StateComponent(myStates);
    }

}
