package auth.helpers;

import gamedata.Game;

import java.util.ArrayList;

public class GameCloudWrapper {
    public Game game;
    public String gameID = "";
    public String owner = "";
    public ArrayList<String> allowAccess = new ArrayList<>();
}
