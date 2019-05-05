package Player.Features.ScrollableWindows;

/**
 * @deprecated
 */
public class CommentSection extends ScrollableWindow {

    String commentHistory;

    public CommentSection() {

    }

    public void addComment(String comment) {
        commentHistory += (comment + "\n");
        addText(commentHistory);
    }

    @Override
    protected void update() {
        commentHistory = "";
        addText(commentHistory);
    }

}
