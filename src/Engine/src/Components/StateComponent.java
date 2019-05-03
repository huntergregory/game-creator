package Engine.src.Components;

<<<<<<< HEAD:src/Engine/src/EngineData/Components/StateComponent.java
import java.util.*;
=======
import java.util.List;
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/StateComponent.java

public class StateComponent extends Component {
    private List<String> myStates;

<<<<<<< HEAD:src/Engine/src/EngineData/Components/StateComponent.java
    public StateComponent(String ... states) {
        myStates = new ArrayList<>();
        myStates.addAll(Arrays.asList(states));
    }

=======
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/StateComponent.java
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
