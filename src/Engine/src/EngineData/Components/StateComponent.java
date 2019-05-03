package Engine.src.EngineData.Components;

import java.util.ArrayList;
import java.util.List;

public class StateComponent extends Component {
    private List<String> myStates;
    private String myCurrState;

    public StateComponent(String ... states) {
        myStates = new ArrayList<>();
        for(String state: states) {
            myStates.add(state);
        }
        myCurrState = myStates.get(0);
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

    public String getState() {
        return myCurrState;
    }

    public void setState(String state) {
        for (String s : myStates) {
            if (state.equals(s)) {
                myCurrState = state;
            }
        }
    }

    @Override
    public Component copy() {
        return new StateComponent(myStates);
    }

}
