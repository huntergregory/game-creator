package Engine.src.EngineData.Components;

import static java.lang.Double.parseDouble;

/**
 * Component that keeps track of the score of an EngineInstance
 * @author David Liu
 */
public class ScoreComponent extends Component{

    private double myScore;

    /**
     * Initializes a score for the EngineInstance
     * @param initialScore the initial score of the EngineInstance
     */
    public ScoreComponent(String initialScore){
        myScore = parseDouble(initialScore);
    }

    /**
     * Gives the score
     * @return the score of the EngineInstance
     */
    public double getScore(){
        return myScore;
    }

    /**
     * Sets the score of the EngineInstance
     * @param score the score
     */
    public void setScore(double score){
        myScore = score;
    }

    /**
     * Gives a copy of the ScoreComponent
     * @return copy of the ScoreComponent
     */
    @Override
    public Component copy() {
        return new ScoreComponent(Double.toString(myScore));
    }

}
