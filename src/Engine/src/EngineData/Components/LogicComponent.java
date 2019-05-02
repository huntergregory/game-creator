package Engine.src.EngineData.Components;

public class LogicComponent extends Component{

    private String myLogic;

    public LogicComponent(String logic){
        myLogic = logic;
    }

    public void setLogic(String logic) {
        myLogic = logic;
    }

    public String getLogic() {
        return myLogic;
    }

    public void addLogic(String logic) {
        myLogic += logic;
    }

    @Override
    public Component copy() {
        return new LogicComponent(myLogic);
    }

}
