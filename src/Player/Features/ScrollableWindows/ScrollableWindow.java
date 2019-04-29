package Player.Features.ScrollableWindows;

import Player.Features.Feature;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.util.LinkedList;

public abstract class ScrollableWindow extends Feature {
    private HBox myHBox;
    private ScrollPane myScrollPane;
    protected TextArea myTextArea;
    private LinkedList<String> myTextChain;

    /**
     * Constructor for a ScrollableWindow object
     */
    public ScrollableWindow() {
        myHBox = new HBox();
        myTextChain = new LinkedList<>();
        myTextArea = new TextArea();
        myScrollPane = new ScrollPane(myTextArea);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
        myHBox.getChildren().add(myScrollPane);
    }

    protected void clearText() {
        myTextArea.setText("");
    }

    protected void addText(String newString) {
        myTextArea.setText(newString);
    }

    abstract protected void update();

    @Override
    protected Node getMainComponent() {
        return myHBox;
    }
}