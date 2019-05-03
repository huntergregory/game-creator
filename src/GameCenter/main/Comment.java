package GameCenter.main;

public class Comment {

    private int myGame;
    private String myUser;
    private String myComment;

    public Comment(int g, String u, String c) {
        myGame = g;
        myUser = u;
        myComment = c;
    }

    public int getMyGame() { return this.myGame; }

    public void setMyGame(int i) { this.myGame = i; }

    public String getMyUser() { return this.myUser; }

    public void setMyUser(String x) { this.myUser = x; }

    public String getMyComment() { return this.myComment; }

    public void setMyComment(String x) { this.myComment = x; }
}
