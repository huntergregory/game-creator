package Engine.src.Components;

public class ImpassableComponent extends Component {
    private boolean myImpassable;

    public ImpassableComponent(boolean impassable) {
        myImpassable = impassable;
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
