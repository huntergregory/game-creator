package Engine.src.Components;

public class LogicComponent extends Component{

    private String myLogic;

    public LogicComponent(String logic){
        myLogic = logic;
    }

    public String getLogic(){
        return myLogic;
    }

    public void setLogic(String logic) { myLogic = logic; }
}
