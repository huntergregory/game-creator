package Engine.src.EngineData.Components;

import java.util.ArrayList;
import java.util.List;

/**
 * Component that keeps track of the possible states of an EngineInstance
 * @author David Liu
 */
public class StateComponent extends Component {
    private List<String> myStates;
    private String myCurrState;

    /**
     * Assumes that the current state is the first one that is listed
     * @param states Any number of strings that represent the possible states of an EngineInstance
     */
    public StateComponent(String ... states) {
        myStates = new ArrayList<>();
        for(String state: states) {
            myStates.add(state);
        }
        myCurrState = myStates.get(0);
    }

    /**
     * Assumes that the current state is the first one that is listed
     * @param states A list of strings that represent the possible states of an EngineInstance
     */
    public StateComponent(List<String> states) {
        myStates = states;
    }

    /**
     * Gives the boolean of whether the state exists in the possible states of an EngineInstance
     * @param state String representing a state that is being checked
     * @return boolean of whether the state exists in the possible states of an EngineInstance
     */
    public boolean hasState(String state){
        return myStates.contains(state);
    }

    /**
     * Adds the string to the list of possible states
     * @param state String representing a state that is being added
     */
    public void addState(String state){
        myStates.add(state);
    }

    /**
     * Removes the string from the list of possible states
     * @param state String representing a state that is being removed
     */
    public void removeState(String state){
        myStates.remove(state);
    }

    /**
     * Returns the current state
     * @return the current state of the EngineInstance
     */
    public String getState() {
        return myCurrState;
    }

    /**
     * Sets the current state
     * @param state String representing the state that is being set
     */
    public void setState(String state) {
        for (String s : myStates) {
            if (state.equals(s)) {
                myCurrState = state;
            }
        }
    }

    /**
     * Gives a copy of the StateComponent
     * @return copy of the StateComponent
     */
    @Override
    public Component copy() {
        return new StateComponent(myStates);
    }

}
