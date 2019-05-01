package Engine.src.EngineData;

import Engine.src.EngineData.Components.Component;

import java.util.Map;

public class EngineInstance extends ComponentContainer {
    private String myType;

    public EngineInstance(String id, String type) {
        super(id);
        myType = type;
    }

    public EngineInstance(String id, String type, Map<Class<? extends Component>, Component> components) {
        super(id, components);
        myType = type;
    }

    public String getType() {
        return myType;
    }
}
