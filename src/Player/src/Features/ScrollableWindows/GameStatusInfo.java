package Features.ScrollableWindows;

public class GameStatusInfo extends ScrollableWindow {

    String gameStatus = "";
    int myLives;
    int myLevel;
    int enemiesAlive;
    int enemiesDead;
    double myX;
    double myY;
    double myZ;
    double timeElapsed;

    public GameStatusInfo() {
        myLives = 0;
        myLevel = 1;
        enemiesAlive = 1;
        enemiesDead = 0;
        myX = 0.0;
        myY = 0.0;
        myZ = 0.0;
        timeElapsed = 0.0;
        updateGameStatus();
    }

    protected void updateGameStatus() {
        gameStatus = "";
        gameStatus += ("Lives: " + myLives + "\n");
        gameStatus += ("Level: " + myLevel + "\n");
        gameStatus += ("Enemies Alive: " + enemiesAlive + "\n");
        gameStatus += ("Enemies Dead: " + enemiesDead + "\n");
        gameStatus += ("X Postion: " + myX + "\n");
        gameStatus += ("Y Postion: " + myY + "\n");
        gameStatus += ("Z Postion: " + myZ + "\n");
        gameStatus += ("Time: " + timeElapsed + "\n");
        addText(gameStatus);
    }

    @Override
    protected void refreshWindow() {
        gameStatus = "";
        addText(gameStatus);
    }

}