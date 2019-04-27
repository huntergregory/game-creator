package Engine.src.Controller;

import java.util.List;

public class DebugLog {
    private List<String> myLog;

    public DebugLog(List<String> log) {
        myLog = log;
    }

    public void addDebugStatement(String log) {
        myLog.add(log);
    }
}
