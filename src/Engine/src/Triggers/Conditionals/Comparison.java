package Engine.src.Triggers.Conditionals;

import Engine.src.Components.Component;
import Engine.src.ECS.EntityManager;

public abstract class Comparison extends ObjectConditional{

    private String myComparisonOperator;
    private Component myCompareTo;
    private Component myComponent;
    protected Class myComponentName;

    public Comparison(boolean required, int obj, String comparisonOperator, Component compareTo){
        super(required, obj);
        myComparisonOperator = comparisonOperator;
        myCompareTo = compareTo;
    }

    public Comparison(boolean required, int obj, String comparisonOperator){
        super(required, obj);
        myComparisonOperator = comparisonOperator;
    }

    public Comparison(boolean required, String comparisonOperator, Component compareTo){
        super(required);
        myComparisonOperator = comparisonOperator;
        myCompareTo = compareTo;
    }

    public Comparison(boolean required, String comparisonOperator){
        super(required);
        myComparisonOperator = comparisonOperator;
    }

    protected boolean checkOperator(String value1, String value2) {
        return (value1.equals(value2) && myComparisonOperator.contains("="));
    }

    protected boolean checkOperator(double value1, double value2) {
        boolean equals = false;
        if (value1 == value2 && myComparisonOperator.contains("=")) equals = true;
        if (myComparisonOperator.contains(">")) return value1 > value2 || equals;
        if (myComparisonOperator.contains(">")) return value1 > value2 || equals;
        return equals;
    }

    abstract boolean compare(EntityManager entityManager);

    @Override
    public boolean satisfied(int obj, EntityManager entityManager) {
        if (myCompareTo == null) {
            myCompareTo = entityManager.getComponent(obj, myComponentName);
        }
        myComponent = entityManager.getComponent(myObject, myComponentName);
        if (!(myComponent == null)) return compare(entityManager);
        else return true;
    }

    @Override
    public boolean satisfied(EntityManager entityManager) {
        myComponent = entityManager.getComponent(myObject, myComponentName);
        if (!(myComponent == null)) return compare(entityManager);
        else return true;
    }

    protected Component getCompareTo(){
        return myCompareTo;
    }

    protected Component getComponent(){
        return myComponent;
    }

    protected String getMyComparisonOperator(){
        return myComparisonOperator;
    }
}
