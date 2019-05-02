package Engine.src.EngineData.Components;

import static java.lang.Boolean.parseBoolean;

public class ImpassableComponent extends Component {
    private boolean myImpassable;

    public ImpassableComponent(String impassable) {
        myImpassable = parseBoolean(impassable);
    }

    public boolean getImpassable() {
        return myImpassable;
    }

    public void setImpassable(boolean impassible) {
        myImpassable = impassible;
    }

    @Override
    public Component copy() {
        return new ImpassableComponent(Boolean.toString(myImpassable));
    }

}
