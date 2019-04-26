package network_account;

import java.util.Map;

/**
 * Object containing all of the information that a user receives when they log in, including the username, display name,
 * and high scores for all games that have been played.
 */
public class UserIdentity {
    private String myUsername;
    private Map<String, Integer> myHighScores;
    private String myName;

    //TODO: Reformat UserIdentity class to reflect only users top score, along with universal top scores

    /**
     * Constructor for the UserIdentity object that contains all of the information unique to the current user after
     * they log in
     * @param username is the String representing the user's username (for login)
     * @param name is the String representing the user's display name
     * @param highScores is a Map of Strings to Integers, with the key being the name of the game and the value being
     *                   the user's highest score for the game
     */
    public UserIdentity(String username, String name, Map<String, Integer> highScores){
        myUsername = username;
        myHighScores = highScores;
        myName = name;
    }

    /**
     * Gets the username associated with this user identity
     * @return
     */
    public String getUsername(){
        return myUsername;
    }

    /**
     * Gets the individual user's high score for the input game, and returns a high score of 0 if the user has never
     * played the game before
     * @param gameName is the String representing the name of the game that the programmer is trying to get the high
     *                 score of
     * @return Integer representing the user's highest score for the input game
     */
    public Integer getHighScores(String gameName){
        try {
            return myHighScores.get(gameName);
        } catch(RuntimeException e){
            return 0;
        }
    }

    /**
     * Getter for the display name of the user account
     * @return String representing the user's display name
     */
    public String getName(){return myName;}
}
