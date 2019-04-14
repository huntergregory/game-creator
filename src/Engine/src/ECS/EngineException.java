package Engine.src.ECS;

public abstract class EngineException extends Exception {
    private String myMessage;

    public EngineException(String message, String error){
        super(error);
        this.myMessage = message;
    }

    public String getMessage(){
        return myMessage;
    }
}
