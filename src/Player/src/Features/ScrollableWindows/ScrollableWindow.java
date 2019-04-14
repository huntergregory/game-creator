package Player.src.Features.ScrollableWindows;

import Player.src.Features.Feature;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import java.util.LinkedList;

public abstract class ScrollableWindow extends Feature {
    private static final boolean SORTS_ALPHABETICALLY = false;
    private static final int MAX_LINES_DISPLAYED = 50;

    private HBox myHBox;
    private Button refresh;
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
        refresh = new Button("Refresh");
        refresh.setMinWidth(80);
        refresh.setOnAction((event) -> refreshWindow());
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
        myHBox.getChildren().add(myScrollPane);
        myHBox.getChildren().add(refresh);
    }

    protected void clearText() {
        myTextArea.setText("");
    }

    protected void addText(String newString) {
        myTextArea.setText(newString);
    }

    abstract protected void refreshWindow();

    @Override
    protected Node getMainComponent() {
        return myHBox;
    }

}