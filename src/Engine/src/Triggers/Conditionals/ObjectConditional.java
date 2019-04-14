package Engine.src.Triggers.Conditionals;

import Engine.src.ECS.EntityManager;

public abstract class ObjectConditional extends Conditional{

    protected int myObject;

    public ObjectConditional(boolean required){
        super(required);
        myObject = -1;
    }

    public ObjectConditional(boolean required, int obj){
        super(required);
        myObject = obj;
    }

    public void setMyObject(int obj){
        myObject = obj;
    }

    @Override
    public abstract boolean satisfied(int obj, EntityManager objectManager);

    @Override
    public abstract boolean satisfied(EntityManager objectManager); // TODO: ideally throw error here

    public int getMyObj(){
        return myObject;
    }

}
