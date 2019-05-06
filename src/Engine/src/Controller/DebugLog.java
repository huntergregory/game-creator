package Engine.src.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to connect with the authoring environment for the author to call in order to print potential
 * debug statements that they would want to through the debug console
 * @author David Liu
 */
public class DebugLog {
    private List<String> myLog;

    /**
     * An ArrayList that contains all debug log messages the author has written to console once groovy scripts have been
     * parsed
     */
    public DebugLog() {
        myLog = new ArrayList<>();
    }

    private void addToLog(String debugLine) {
        myLog.add(debugLine);
    }

    /**
     * Lowest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void verbose(String message) {
        String debugLine = "Verbose - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Second-lowest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void info(String message) {
        String debugLine = "Info - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Third-lowest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void warning(String message) {
        String debugLine = "Warning - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Middle rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void debug(String message) {
        String debugLine = "Debug - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Third-highest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void err(String message) {
        String debugLine = "Error - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Second-highest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void critical(String message) {
        String debugLine = "Critical Error - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    /**
     * Highest rung of severity on the debug log
     * @param message the author's debug statement
     */
    public void wtf(String message) {
        String debugLine = "WTF - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    private String getTimeStamp() {
        Date date = new Date();
        DateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeStamp.format(date);
    }

    /**
     * Gives the log to the player to potentially display if the author is in debug mode
     * @return the debug log (ArrayList of Strings)
     */
    public List<String> getLog() {
        return myLog;
    }
}
