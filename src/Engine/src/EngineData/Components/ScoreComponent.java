package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

public class ScoreComponent extends Component{

    private double myScore;

    public ScoreComponent(String initialScore){
        myScore = parseDouble(initialScore);
    }

    public double getScore(){
        return myScore;
    }

    public void setScore(double score){
        myScore = score;
    }

}
