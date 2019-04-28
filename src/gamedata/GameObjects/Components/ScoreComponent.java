package gamedata.GameObjects.Components;

public class ScoreComponent extends Component{

    private double myScore;

    public ScoreComponent(double initialScore){
        myScore = initialScore;
    }

    public double getScore(){
        return myScore;
    }

    public void setScore(double score){
        myScore = score;
    }

}
