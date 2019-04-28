package Player.Features;

import javafx.scene.Node;

public abstract class Feature {

    public Feature() {
        // TODO: make superclass
        // We can make this into an interface if this is all we need
    }

    abstract protected Node getMainComponent();

}
