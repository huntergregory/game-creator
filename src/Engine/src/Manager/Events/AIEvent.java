package Engine.src.Manager.Events;

import Engine.src.ECS.Line;
import Engine.src.ECS.Pair;
import gamedata.Game;
import gamedata.GameObjects.Components.BasicComponent;
import gamedata.GameObjects.Components.Component;
import gamedata.GameObjects.Components.HealthComponent;
import gamedata.GameObjects.Instance;

import java.util.ArrayList;
import java.util.List;

public abstract class AIEvent extends ComponentDependentEvent{

    public AIEvent(Game game, Class<?>... parameterTypes) {
        super(game, BasicComponent.class, parameterTypes);
    }

    protected boolean targetEntityObscured(Instance targetInstance, Instance referenceInstance) {
        BasicComponent targetBasic = targetInstance.getComponent(BasicComponent.class);
        double targetX = targetBasic.getX();
        double targetY = targetBasic.getY();
        return obscured(targetX, targetY, targetInstance.getID(), referenceInstance);
    }

    protected boolean targetPointObscured(double targetLocationX, double targetLocationY, Instance referenceInstance){
        return obscured(targetLocationX, targetLocationY, "EMPTY", referenceInstance);
    }

    private boolean obscured(double targetLocationX, double targetLocationY, String targetID, Instance referenceInstance) {
        BasicComponent referenceBasic = referenceInstance.getComponent(BasicComponent.class);
        double referenceX = referenceBasic.getX();
        double referenceY = referenceBasic.getY();

        for (Instance instance : myInstances){
            String ID = instance.getID();
            if (!ID.equals(targetID) && !ID.equals(referenceInstance.getID())){
                BasicComponent basic = instance.getComponent(BasicComponent.class);
                Pair<Double> topLeftCorner = new Pair(basic.getX(), basic.getY());
                Pair<Double> bottomRightCorner = new Pair(basic.getX() + basic.getWidth(), basic.getY() + basic.getHeight());

                Line line1 = new Line(targetLocationX, targetLocationY, referenceX, referenceY);
                Line line2 = new Line(topLeftCorner.getItem1(), topLeftCorner.getItem2(), bottomRightCorner.getItem1(), bottomRightCorner.getItem2());

                return line1.intersects(line2);
            }
        }

        return false;
    }
}
