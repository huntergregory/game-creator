package Engine.src.Controller.Events;

import Engine.src.Controller.NoInstanceException;
import Engine.src.Controller.NoObjectException;
import Engine.src.EngineData.Components.BasicComponent;
import Engine.src.EngineData.EngineInstance;
import Engine.src.EngineData.UnmodifiableEngineGameObject;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Event {
    private static final String ERROR_MESSAGE = "Not the correct number of arguments.";

    protected Map<String, EngineInstance> myEngineInstances;
    protected Set<UnmodifiableEngineGameObject> myGameEngineObjects;
    private Class[] myParameters;

    public Event(Map<String, EngineInstance> engineInstances, Set<UnmodifiableEngineGameObject> engineObjects, Class ... parameterTypes) {
        myEngineInstances = engineInstances;
        myGameEngineObjects = engineObjects;
        myParameters = parameterTypes;
    }

    public Class[] getNonInstanceParameters() {
        return myParameters;
    }

    public void activate(EngineInstance engineInstance, double stepTime, Object ... args) throws IllegalArgumentException {
        if (!parametersMatch(args))
            throw new IllegalArgumentException(ERROR_MESSAGE);
        execute(engineInstance, stepTime, args);
    }

    private boolean parametersMatch(Object ... args) {
        if (args.length != myParameters.length)
            return false;

        for (int k=0; k<args.length; k++) {
            if (!myParameters[k].isInstance(args[0]))
                return false;
        }
        return true;
    }

    protected double calculateAngle(double[] distanceVec){
        double angle = Math.atan(distanceVec[1] / distanceVec[0]);
        if ((distanceVec[0] < 0 && distanceVec[1] < 0) ||
                (distanceVec[0] < 0 && distanceVec[1] > 1))
            angle += Math.PI;
        return angle;
    }

    /**
     *
     * All subclasses of Event assume that Event does full error checking of both the number and type of args in execute.
     * @param engineInstance
     * @param args
     */
    protected abstract void execute(EngineInstance engineInstance, double stepTime, Object ... args);

    protected UnmodifiableEngineGameObject getObjectByID(String id) throws NoObjectException {
        for (UnmodifiableEngineGameObject engineGameObject : myGameEngineObjects) {
            if (engineGameObject.getID().equals(id))
                return engineGameObject;
        }
        throw new NoObjectException(id);
    }

    protected EngineInstance getInstanceByID(String id) throws NoInstanceException {
        if (myEngineInstances.containsKey(id))
            return myEngineInstances.get(id);
        throw new NoInstanceException(id);
    }

    protected EngineInstance createFromInstance(String objectType, EngineInstance creator) throws NoObjectException {
        var newInstance = create(objectType);
        var newBasic = newInstance.getComponent(BasicComponent.class);
        var creatorBasic = creator.getComponent(BasicComponent.class);
        newBasic.setX(creatorBasic.getX());
        newBasic.setY(creatorBasic.getY());
        return newInstance;
    }

    protected EngineInstance createInstance(String objectType, double x, double y)  throws NoObjectException {
        var newInstance = create(objectType);
        var basic =  newInstance.getComponent(BasicComponent.class);
        basic.setX(x);
        basic.setY(y);
        return newInstance;
    }

    private EngineInstance create(String objectType) throws NoObjectException {
        for (UnmodifiableEngineGameObject engineGameObject : myGameEngineObjects) {
            if (objectType.equals(engineGameObject.getID())) {
                String ID = getUntakenID(engineGameObject.getID());
                EngineInstance instance = engineGameObject.createInstance(ID);
                myEngineInstances.put(ID, instance);
                return instance;
            }
        }
        throw new NoObjectException(objectType);
    }

    private void setBasic(double x, double y, double width, double height, BasicComponent basic) {
        basic.setX(x);
        basic.setY(y);
        basic.setWidth(width);
        basic.setHeight(height);
    }

    private String getUntakenID(String objectType) {
        boolean idTaken = true;
        Random rand = new Random();
        String id = Double.toString(rand.nextDouble());

        do{
            id = Double.toString(rand.nextDouble());
            if (!myEngineInstances.containsKey(id)) idTaken = false;

        } while(idTaken);

        return id;
    }
}
