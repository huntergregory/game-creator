package gamedata.NewData;

public class Instance extends ComponentContainer {
    private String myType;

    public Instance(String id, String type) {
        super(id);
        myType = type;
    }

    public String getType() {
        return myType;
    }
}
