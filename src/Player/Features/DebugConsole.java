package Player.Features;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class DebugConsole extends Feature {
    private HBox myHBox;
    private ScrollPane myScrollPane;
    protected Label myTextArea;
    protected String text = "";

    /**
     * Constructor for a DebugConsole object
     */
    public DebugConsole(double width, double height) {
        myHBox = new HBox();
        myTextArea = new Label();
        myTextArea.setMinHeight(height);
        myTextArea.setMinWidth(width);
        myTextArea.setMaxWidth(width);
        myScrollPane = new ScrollPane(myTextArea);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
        myScrollPane.setMinHeight(height);
        myScrollPane.setMinWidth(width);
        myScrollPane.setMaxWidth(width);
        myHBox.getChildren().add(myScrollPane);
    }

    public void clearText() {
        myTextArea.setText("");
    }

    public void addText(String newString) {
        myTextArea.setText(newString);
    }

    public void update(String s) {
        clearText();
        text += s;
        addText(text);
    }

    @Override
    public Node getMainComponent() {
        return myHBox;
    }
}
