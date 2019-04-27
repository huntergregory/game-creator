package gamedata.GameObjects;

public class GameObject extends ComponentContainer {

    public GameObject(String id) {
        super(id);
    }

    public Instance createInstance(String id) {
        return new Instance(id, getID(), getComponents());
    }
}
