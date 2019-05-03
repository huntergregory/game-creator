package Engine.src.EngineData.Components;

import java.util.ArrayList;
import java.util.List;

public class StateComponent extends Component {
    private List<String> myStates;

    public StateComponent(String ... states) {
        myStates = new ArrayList<>();
        for(String state: states) {
            myStates.add(state);
        }
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
