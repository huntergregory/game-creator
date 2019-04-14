package Player.src.Features.ScrollableWindows;

public class CommentSection extends ScrollableWindow {

    String commentHistory;

    public CommentSection() {

    }

    public void addComment(String comment) {
        commentHistory += (comment + "\n");
        addText(commentHistory);
    }

    @Override
    protected void refreshWindow() {
        commentHistory = "";
        addText(commentHistory);
    }

}
