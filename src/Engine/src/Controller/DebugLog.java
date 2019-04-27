package Engine.src.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebugLog {
    private List<String> myLog;

    public DebugLog() {
        myLog = new ArrayList<String>();
    }

    private void addToLog(String debugLine) {
        myLog.add(debugLine);
    }

    public void verbose(String message) {
        String debugLine = "Verbose - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void info(String message) {
        String debugLine = "Info - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void warning(String message) {
        String debugLine = "Warning - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void debug(String message) {
        String debugLine = "Debug - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void err(String message) {
        String debugLine = "Error - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void critical(String message) {
        String debugLine = "Critical Error - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    public void wtf(String message) {
        String debugLine = "WTF - " + getTimeStamp() + " - " + message;
        addToLog(debugLine);
    }

    private String getTimeStamp() {
        Date date = new Date();
        DateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeStamp.format(date);
    }

    public List<String> getLog() {
        return myLog;
    }
}
