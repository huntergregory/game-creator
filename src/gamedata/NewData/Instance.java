package gamedata.NewData;

import gamedata.Components.Component;

import java.util.Map;

public class Instance extends ComponentContainer {
    private String myType;

    public Instance(String id, String type) {
        super(id);
        myType = type;
    }

    public Instance(String id, String type, Map<Class<? extends Component>, Component> components) {
        super(id, components);
        myType = type;
    }

    public String getType() {
        return myType;
    }
}
