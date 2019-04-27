package Player.src.Features;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 *
 * @author Carter Gay
 */
public class SidePanel {
    public static final double V_GAP = 30;
    private static final Color GREEN = Color.web("0x027a50");
    private static final Background BACKGROUND = new Background(new BackgroundFill(GREEN, null, null));

    private GridPane myPane;
    private ArrayList<Node> myChildren;
    private double myNormalWidth;
    private int myNumRows;


    public SidePanel(double width) {
        initializeGridPane();
        myNormalWidth = width;
        myChildren = new ArrayList<>();
        myNumRows = 0;
        updatePanelSize();
    }


    public Pane getPane() {
        return myPane;
    }


    public void addRow(Node node) {
        myPane.addRow(myNumRows, node);
        myChildren.add(node);
        myNumRows += 1;
        updatePanelSize();
    }

    public void removeRow(int index) {
        myPane.getChildren().remove(index);
        myNumRows -= 1;
        updatePanelSize();
    }


    private void initializeGridPane() {
        myPane = new GridPane();
        myPane.setMaxWidth(0);
        myPane.setVgap(V_GAP);
        myPane.setBackground(BACKGROUND);
    }


    private void updatePanelSize() {
        if (myNumRows >= 1)
            expandPanel();
        else
            shrinkPanel();
    }

    private void expandPanel() {
        myPane.setMinWidth(myNormalWidth);
        myPane.setMaxWidth(myNormalWidth);
    }

    private void shrinkPanel() {
        myPane.setMinWidth(0);
        myPane.setMaxWidth(0);
    }
}
