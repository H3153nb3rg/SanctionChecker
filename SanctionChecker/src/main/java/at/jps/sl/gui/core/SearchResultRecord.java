package at.jps.sl.gui.core;

public class SearchResultRecord {
    private String ListName;
    private String ListId;
    private String Token;
    private String Comment;

    public String getListName() {
        return ListName;
    }

    public void setListName(final String listName) {
        ListName = listName;
    }

    public String getListId() {
        return ListId;
    }

    public void setListId(final String listId) {
        ListId = listId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(final String token) {
        Token = token;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(final String comment) {
        Comment = comment;
    }
}
