package Player.Features;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class DebugConsole extends Feature {
    private HBox myHBox;
    private ScrollPane myScrollPane;
    protected Label myTextArea;
    protected String text = "";
    private List<String> debugLog = new ArrayList<>();
    private static final double OFFSET = 50.0;

    /**
     * Constructor for a DebugConsole object
     */
    public DebugConsole(double width, double height) {
        myHBox = new HBox();
        myTextArea = new Label();
        myTextArea.setMinHeight(height - OFFSET);
        myTextArea.setMinWidth(width - OFFSET);
        myTextArea.setMaxWidth(width - OFFSET);
        myScrollPane = new ScrollPane(myTextArea);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
        myScrollPane.setMinHeight(height - OFFSET);
        myScrollPane.setMinWidth(width - OFFSET);
        myScrollPane.setMaxWidth(width - OFFSET);
        myHBox.getChildren().add(myScrollPane);
    }

    public void clearText() {
        myTextArea.setText("");
    }

    public void addText(String newString) {
        myTextArea.setText(newString);
    }

    public void update(List<String> s) {
        if (s.size() <= debugLog.size()) {
            return;
        }
        for (int k = debugLog.size() - 1; k < s.size(); k++) {
            text += s.get(k);
            if (k != s.size() - 1) {
                text += "\n";
            }
        }
        addText(text);
    }

    @Override
    public Node getMainComponent() {
        return myHBox;
    }
}
