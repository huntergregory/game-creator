package Engine.src.Components;

public class LogicComponent extends Component{

    private String myLogic;

    public LogicComponent(String logic){
        myLogic = logic;
    }

    public String getLogic(){
        return myLogic;
    }

<<<<<<< HEAD:src/Engine/src/EngineData/Components/LogicComponent.java
    public void addLogic(String logic) {
        myLogic += logic;
    }

    @Override
    public Component copy() {
        return new LogicComponent(myLogic);
    }

=======
    public void setLogic(String logic) { myLogic = logic; }
>>>>>>> ac73cab8a1d864ca81a255c5a6ae47167f4024dc:src/Engine/src/Components/LogicComponent.java
}
