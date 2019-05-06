package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * A component representing score.
 * @author Hunter Gregory
 * @author David Liu
 */
public class ScoreComponent extends Component{

    private double myScore;

    /**
     * Create a ScoreComponent
     * @param initialScore
     */
    public ScoreComponent(String initialScore){
        myScore = parseDouble(initialScore);
    }

    /**
     * @return score
     */
    public double getScore(){
        return myScore;
    }

    /**
     * Set the score
     * @param score
     */
    public void setScore(double score){
        myScore = score;
    }

    @Override
    public Component copy() {
        return new ScoreComponent(Double.toString(myScore));
    }
}
