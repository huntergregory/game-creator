package Engine.src.EngineData.Components;

import static java.lang.Boolean.parseBoolean;

/**
 * Component that keeps track of whether an EngineInstance is impassable or not
 * @author David Liu
 */
public class ImpassableComponent extends Component {
    private boolean myImpassable;

    /**
     * Assumes impassable is a boolean
     * @param impassable boolean to set impassable condition
     */
    public ImpassableComponent(String impassable) {
        myImpassable = parseBoolean(impassable);
    }

    /**
     * Gives impassable boolean
     * @return impassable boolean
     */
    public boolean getImpassable() {
        return myImpassable;
    }

    /**
     * Sets impassable boolean
     * @param impassible new impassable condition
     */
    public void setImpassable(boolean impassible) {
        myImpassable = impassible;
    }

    /**
     * Gives a copy of the ImpassableComponent
     * @return copy of the ImpassableComponent
     */
    @Override
    public Component copy() {
        return new ImpassableComponent(Boolean.toString(myImpassable));
    }

}
