package Player.src.Features;

import Player.src.Features.ScrollableWindows.CommentSection;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CommentBox extends Feature {

    private static final String PROMPT = "Enter Comment";
    private static final String UPLOAD_BUTTON = "UPLOAD";
    private static final double COMMENT_STRETCH_WIDTH = 1000;
    private static final double BUTTON_WIDTH = 100;
    private static final double BUTTON_STRETCH_HEIGHT = 60;

    private Button myUploadButton;
    private TextArea myCommentInput;
    private String myComment;
    private CommentSection myCommentSection;

    /**
     * Constructor for CommentBox and its buttons for uploading. Creates listeners for buttons
     */
    public CommentBox(CommentSection commentSection) {
        myUploadButton = new Button(UPLOAD_BUTTON);
        myUploadButton.setOnAction((event) -> uploadComment());
        myCommentInput = new TextArea();
        myCommentInput.setPrefRowCount(10);
        myCommentInput.setPrefColumnCount(10);
        myCommentInput.setPromptText(PROMPT);
        myCommentSection = commentSection;
    }

    /**
     * Populate the text of the CommandTerminal
     * @param command
     */
    public void setText(String command) {
        myCommentInput.setText(command);
    }

    /**
     * Send the text to the CommandParser when Parse button is pressed
     */
    public void uploadComment() {
        myComment = myCommentInput.getText();
        myCommentInput.setText("");
        myCommentInput.setPromptText(PROMPT);
        try {
            myCommentSection.addComment(myComment);
        }
        catch (Exception e) {
            myCommentInput.setPromptText("--" + e.getMessage() + "-- Enter a valid comment");
        }
    }

    /**
     * Add the CommandTerminal and buttons to the GridPane
     * @return
     */
    public Pane getPane() {
        var gridPane = new GridPane();
        myCommentInput.setPrefWidth(COMMENT_STRETCH_WIDTH); //stretch out text area
        setButtonSize(myUploadButton);
        gridPane.addRow(0, myCommentInput, myUploadButton);
        return gridPane;
    }

    private void setButtonSize(Button button) {
        button.setMinWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_STRETCH_HEIGHT);
    }

    @Override
    protected Node getMainComponent() {
        return null;
    }

}
