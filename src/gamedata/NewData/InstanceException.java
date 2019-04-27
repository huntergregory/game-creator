package gamedata.NewData;

public abstract class InstanceException extends RuntimeException {
    private String myMessage;

    public InstanceException(String message, String error){
        super(error);
        this.myMessage = message;
    }

    public String getMessage(){
        return myMessage;
    }
}
